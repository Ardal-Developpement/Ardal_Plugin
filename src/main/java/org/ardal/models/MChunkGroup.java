package org.ardal.models;

public class MChunkGroup {
    private Long chunk_id;
    private int chunk_id_group;

    public MChunkGroup(Long chunk_id, int chunk_id_group) {
        this.chunk_id = chunk_id;
        this.chunk_id_group = chunk_id_group;
    }

    public Long getChunkId() {
        return chunk_id;
    }

    public void setChunkId(Long chunk_id) {
        this.chunk_id = chunk_id;
    }

    public int getChunkIdGroup() {
        return chunk_id_group;
    }

    public void setChunkIdGroup(int chunk_id_group) {
        this.chunk_id_group = chunk_id_group;
    }
}
