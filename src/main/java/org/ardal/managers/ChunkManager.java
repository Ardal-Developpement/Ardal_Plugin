package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.chunks.ChunkManagerInfo;
import org.ardal.api.commands.ArdalCmdNode;
import org.ardal.api.managers.ArdalManager;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.chunk.add.AddChunkCmd;
import org.ardal.db.Database;
import org.ardal.models.MChunk;
import org.ardal.objects.ChunkGroupObj;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ChunkManager extends ArdalCmdNode implements ChunkManagerInfo, ArdalManager, Listener {
    private final HashMap<Long, MChunk> savedChunks;
    private final List<ChunkGroupObj> chunkGroups;

    public ChunkManager() {
        super(BaseCmdAlias.BASE_CHUNK_CMD_ALIAS);

        this.savedChunks = new HashMap<>();
        this.chunkGroups = new ArrayList<>();

        this.registerCmd(new AddChunkCmd());

        Ardal.getInstance().getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }

    @Override
    public void onEnable() {
        // load saved chunk cache
        Set<Integer> groupIds = new HashSet<>();
        for(MChunk chunk : Ardal.getInstance().getDb().gettChunk().getAllChunks()) {
            this.savedChunks.put(chunk.getChunckId(), chunk);
            groupIds.add(chunk.getChunkGroupId());
        }

        for(Integer groupId : groupIds) {
            this.chunkGroups.add(new ChunkGroupObj(groupId));
        }
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (e.getFrom().getChunk() != e.getTo().getChunk()) {
            long cI = GetChunkId(e.getTo().getChunk());
            if(this.savedChunks.containsKey(cI)) {
                System.out.println("You are in a saved chunk");

                //
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
        ChunkGroupObj chunkGroup = this.chunkGroups.get(mChunk.getChunkGroupId());
        if(chunkGroup == null) {
            chunkGroup = this.addNewChunkGroup();
        }

        return chunkGroup.addChunkInGroup(mChunk);
    }

    @Override
    public boolean removeChunkFromGroup(MChunk mChunk) {
        ChunkGroupObj chunkGroup = this.chunkGroups.get(mChunk.getChunkGroupId());
        if(chunkGroup == null) {
            return false;
        }

        return chunkGroup.deleteChunkInGroup(mChunk);
    }

    @Override
    public ChunkGroupObj addNewChunkGroup() {
        Database db = Ardal.getInstance().getDb();
        int groupId = db.gettGroups().createGroup();

        ChunkGroupObj chunkGroupObj = new ChunkGroupObj(groupId);
        this.chunkGroups.add(chunkGroupObj);

        return chunkGroupObj;
    }

    @Override
    public boolean deleteChunkGroup(int chunkGroupId) {
        ChunkGroupObj chunkGroup = this.chunkGroups.get(chunkGroupId);
        if(chunkGroup == null) {
            return false;
        }

        if(Ardal.getInstance().getDb().gettChunk().deleteChunkGroup(chunkGroupId)) {
            this.chunkGroups.remove(chunkGroupId);
            return true;
        }

        return false;
    }

    @Override
    public boolean chunkIsSaved(Long chunkId, int chunkGroupId) {
        ChunkGroupObj chunkGroupObj = this.chunkGroups.get(chunkGroupId);
        if(chunkGroupObj == null) {
            return false;
        }

        return chunkGroupObj.containChunk(chunkId);
    }

    @Override
    public boolean chunkGroupExist(int chunkGroupId) {
        for(ChunkGroupObj chunkGroup : this.chunkGroups) {
            if(chunkGroup.getChunkGroupId() == chunkGroupId) {
                return true;
            }
        }

        return false;
    }
}
