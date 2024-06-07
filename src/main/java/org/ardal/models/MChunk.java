package org.ardal.models;

public class MChunk {
    private Long chunk_id;
    private int chunk_group_id;

    public MChunk(Long id, int chunk_group_id) {
        this.chunk_id = id;
        this.chunk_group_id = chunk_group_id;
    }

    public Long getChunkId() {
        return chunk_id;
    }

    public int getChunkGroupId() {
        return chunk_group_id;
    }
}
