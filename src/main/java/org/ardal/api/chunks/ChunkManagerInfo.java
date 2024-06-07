package org.ardal.api.chunks;

import org.ardal.models.MChunk;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ChunkManagerInfo {
    boolean addChunkInGroup(MChunk mChunk);
    boolean removeChunkFromGroup(MChunk mChunk);

    ChunkGroupObj addNewChunkGroup(List<ChunkModifierType> modifierTypes);
    boolean deleteChunkGroup(int chunkGroupId);

    @Nullable MChunk getChunkById(Long chunkId);
    @Nullable ChunkGroupObj getChunkGroupObj(int chunkGroupId);

    boolean chunkIsSaved(Long chunkId);
    boolean chunkGroupExist(int chunkGroupId);
}
