package org.ardal.api.chunks;

import org.ardal.models.MChunk;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ChunkManagerInfo {
    boolean addChunkInGroup(MChunk mChunk);
    boolean removeChunkFromGroup(MChunk mChunk);

    ChunkGroupObj addNewChunkGroup(int chunkGroupId, List<ChunkModifierType> modifierTypes);
    boolean deleteChunkGroup(int chunkGroupId);

    boolean chunkIsSaved(Long chunkId);
    @Nullable ChunkGroupObj getChunkGroupObj(int chunkGroupId);
    boolean chunkGroupExist(int chunkGroupId);
}
