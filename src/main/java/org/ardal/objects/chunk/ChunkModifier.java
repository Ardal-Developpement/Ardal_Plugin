package org.ardal.objects.chunk;

import org.ardal.api.chunks.ChunkModifierType;
import org.bukkit.entity.Player;

public abstract class ChunkModifier {
    private final ChunkGroupObj chunkGroupObj;
    private final ChunkModifierType chunkModifierType;

    public ChunkModifier(ChunkGroupObj chunkGroupObj, ChunkModifierType chunkModifierType) {
        this.chunkGroupObj = chunkGroupObj;
        this.chunkModifierType = chunkModifierType;
    }

    public abstract void onPlayerEnter(Player player);

    public ChunkGroupObj getChunkGroupObj() {
        return chunkGroupObj;
    }

    public ChunkModifierType getChunkModifierType() {
        return chunkModifierType;
    }
}
