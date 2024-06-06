package org.ardal.api.chunks;

import org.ardal.models.MChunk;
import org.ardal.objects.ChunkGroupObj;
import org.jetbrains.annotations.Nullable;

public interface ChunkManagerInfo {
    boolean addChunkInGroup(MChunk mChunk);
    boolean removeChunkFromGroup(MChunk mChunk);

    ChunkGroupObj addNewChunkGroup(int chunkGroupId, ChunkGroupType type);
    boolean deleteChunkGroup(int chunkGroupId);

    boolean chunkIsSaved(Long chunkId);
    @Nullable ChunkGroupObj getChunkGroupObj(int chunkGroupId);
    boolean chunkGroupExist(int chunkGroupId);
}
