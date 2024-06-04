package org.ardal.api.chunks;

import org.ardal.models.MChunk;

public interface ChunkManagerInfo {
    boolean addChunkInGroup(MChunk mChunk);
    boolean removeChunkFromGroup(MChunk mChunk);

    Integer addNewChunkGroup();
    boolean deleteChunkGroup(int chunkGroupId);
}
