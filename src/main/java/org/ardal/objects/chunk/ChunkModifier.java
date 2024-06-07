package org.ardal.objects.chunk;

import org.bukkit.entity.Player;

public abstract class ChunkModifier {
    private final ChunkGroupObj chunkGroupObj;

    public ChunkModifier(ChunkGroupObj chunkGroupObj) {
        this.chunkGroupObj = chunkGroupObj;
    }

    public abstract void onPlayerEnter(Player player);

    public ChunkGroupObj getChunkGroupObj() {
        return chunkGroupObj;
    }
}
