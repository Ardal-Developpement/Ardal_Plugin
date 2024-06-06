package org.ardal.models;

import org.ardal.api.chunks.ChunkGroupType;

public class MChunk {
    private Long chunk_id;
    private int chunk_group_id;
    private ChunkGroupType type;

    public MChunk(Long id, int chunk_group_id, ChunkGroupType type) {
        this.chunk_id = id;
        this.chunk_group_id = chunk_group_id;
        this.type = type;
    }

    public Long getChunkId() {
        return chunk_id;
    }

    public int getChunkGroupId() {
        return chunk_group_id;
    }

    public ChunkGroupType getType() {
        return type;
    }
}
