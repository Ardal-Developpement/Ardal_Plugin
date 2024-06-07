package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.chunks.ChunkManagerInfo;
import org.ardal.api.chunks.ChunkModifierType;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.chunk.add.AddChunkManager;
import org.ardal.commands.chunk.remove.RemoveChunkManager;
import org.ardal.models.MChunk;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.ardal.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ChunkManager extends ArdalCmdManager implements ChunkManagerInfo, ArdalManager, Listener {
    private final HashMap<Long, MChunk> savedChunks;
    private final List<ChunkGroupObj> chunkGroups;

    public ChunkManager() {
        super(BaseCmdAlias.BASE_CHUNK_CMD_ALIAS);

        this.savedChunks = new HashMap<>();
        this.chunkGroups = new ArrayList<>();

        this.registerCmd(new AddChunkManager());
        this.registerCmd(new RemoveChunkManager());

        Ardal.getInstance().getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }

    @Override
    public void onEnable() {
        // load saved chunk cache
        Set<Integer> groupIds = new HashSet<>();
        for(MChunk chunk : Ardal.getInstance().getDb().gettChunk().getAllChunks()) {
            this.savedChunks.put(chunk.getChunkId(), chunk);
            groupIds.add(chunk.getChunkGroupId());
        }

        for(Integer groupId : groupIds) {
            this.chunkGroups.add(new ChunkGroupObj(groupId));
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Command can be only used by player.");
            return true;
        }

        return this.onSubCmd(commandSender, command, s, StringUtils.getStrListFromStrArray(strings));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (e.getFrom().getChunk() != e.getTo().getChunk()) {
            long cI = GetChunkId(e.getTo().getChunk());
            for(MChunk chunk : savedChunks.values()) {
                if(chunk.getChunkId().equals(cI)) {
                    ChunkGroupObj chunkGroupObj = this.getChunkGroupObj(chunk.getChunkGroupId());
                    chunkGroupObj.onPlayerEnter(e.getPlayer());
                }
            }
        }
    }

    public static long GetChunkId(Chunk c) {
        int x = c.getX();
        int z = c.getZ();
        World w = c.getWorld();
        long wI = w.getUID().getMostSignificantBits() ^ w.getUID().getLeastSignificantBits();
        long cI = ((long) x << 32) | (z & 0xFFFFFFFFL);
        cI ^= wI;
        return cI;
    }

    @Nullable
    public static Location getChunkLocationFromChunkId(long chunkId, UUID worldUUID) {
        long worldUUIDBits = worldUUID.getMostSignificantBits() ^ worldUUID.getLeastSignificantBits();

        long combined = chunkId ^ worldUUIDBits;

        int chunkX = (int) (combined >> 32);
        int chunkZ = (int) combined;

        World world = Bukkit.getWorld(worldUUID);
        if (world != null) {
            return new Location(world, chunkX << 4, 0, chunkZ << 4);
        }
        return null;
    }

    @Override
    public boolean addChunkInGroup(MChunk mChunk) {
        ChunkGroupObj chunkGroup = this.getChunkGroupObj(mChunk.getChunkGroupId());
        if(chunkGroup == null) {
            //If the group do not exist:
            chunkGroup = this.addNewChunkGroup(mChunk.getChunkGroupId(), mChunk.getModifierTypes());
        }

        this.savedChunks.put(mChunk.getChunkId(), mChunk);
        return chunkGroup.addChunkInGroup(mChunk);
    }

    @Override
    public boolean removeChunkFromGroup(MChunk mChunk) {
        ChunkGroupObj chunkGroup = this.getChunkGroupObj(mChunk.getChunkGroupId());
        if(chunkGroup == null) {
            return false;
        }

        return chunkGroup.deleteChunkInGroup(mChunk);
    }

    @Override
    public ChunkGroupObj addNewChunkGroup(int chunkGroupId, List<ChunkModifierType> modifierTypes) {
        return new ChunkGroupObj(chunkGroupId);
    }

    @Override
    public boolean deleteChunkGroup(int chunkGroupId) {
        ChunkGroupObj chunkGroup = this.getChunkGroupObj(chunkGroupId);
        if(chunkGroup == null) {
            return false;
        }

        if(Ardal.getInstance().getDb().gettChunk().deleteChunkGroup(chunkGroupId)) {
            this.chunkGroups.remove(chunkGroup);
            return true;
        }

        return false;
    }

    @Override
    public boolean chunkIsSaved(Long chunkId) {
        return this.savedChunks.containsKey(chunkId);
    }

    @Override
    @Nullable
    public ChunkGroupObj getChunkGroupObj(int chunkGroupId) {
        for(ChunkGroupObj chunkGroup : this.chunkGroups) {
            if(chunkGroup.getChunkGroupId() == chunkGroupId) {
                return chunkGroup;
            }
        }

        return null;
    }

    @Override
    public boolean chunkGroupExist(int chunkGroupId) {
        return this.getChunkGroupObj(chunkGroupId) != null;
    }
}
