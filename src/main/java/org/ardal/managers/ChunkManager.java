package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.managers.ArdalManager;
import org.ardal.models.MChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class ChunkManager implements ArdalManager, Listener {
    private final HashMap<Long, MChunk> savedChunks;

    public ChunkManager() {
        this.savedChunks = new HashMap<>();

        Ardal.getInstance().getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }

    @Override
    public void onEnable() {
        // load saved chunk cache
        for(MChunk chunk : Ardal.getInstance().getDb().gettChunk().getAllChunks()) {
            this.savedChunks.put(chunk.getChunckId(), chunk);
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
}
