package org.ardal.api.chunks;

import org.ardal.models.MChunk;
import org.ardal.objects.ChunkGroupObj;

public interface ChunkManagerInfo {
    boolean addChunkInGroup(MChunk mChunk);
    boolean removeChunkFromGroup(MChunk mChunk);

    ChunkGroupObj addNewChunkGroup();
    boolean deleteChunkGroup(int chunkGroupId);

    boolean chunkIsSaved(Long chunkId, int chunkGroupId);
    boolean chunkGroupExist(int chunkGroupId);
}
