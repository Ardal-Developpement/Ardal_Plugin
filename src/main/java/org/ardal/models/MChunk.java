package org.ardal.models;

import org.ardal.api.chunks.ChunkModifierType;

import java.util.ArrayList;
import java.util.List;

public class MChunk {
    private Long chunk_id;
    private int chunk_group_id;
    private List<ChunkModifierType> modifierTypes;

    public MChunk(Long id, int chunk_group_id, List<ChunkModifierType> modifierTypes) {
        this.chunk_id = id;
        this.chunk_group_id = chunk_group_id;
        this.modifierTypes = modifierTypes;
    }

    public MChunk(Long id, int chunk_group_id, String modifierTypesAsString) {
        this.chunk_id = id;
        this.chunk_group_id = chunk_group_id;

        this.modifierTypes = new ArrayList<>();
        for(String strModifierType : modifierTypesAsString.split("\\|")) {
            this.modifierTypes.add(ChunkModifierType.getTypeFromString(strModifierType));
        }
    }



    public Long getChunkId() {
        return chunk_id;
    }

    public int getChunkGroupId() {
        return chunk_group_id;
    }

    public List<ChunkModifierType> getModifierTypes() {
        return modifierTypes;
    }

    public String getModifierTypesAsString() {
        StringBuilder sb = new StringBuilder();
        for(ChunkModifierType modifierType : this.modifierTypes) {
            sb.append(modifierType.toString()).append('|');
        }

        return sb.toString();
    }
}
