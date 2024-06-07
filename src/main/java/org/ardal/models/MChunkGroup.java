package org.ardal.models;

import org.ardal.api.chunks.ChunkModifierType;

import java.util.ArrayList;
import java.util.List;

public class MChunkGroup {
    private final int chunk_id_group;
    private final List<ChunkModifierType> modifierTypes;

    public MChunkGroup(int chunkIdGroup, List<ChunkModifierType> modifierTypes) {
        this.chunk_id_group = chunkIdGroup;
        this.modifierTypes = modifierTypes;
    }

    public MChunkGroup(int chunkIdGroup, String modifierTypesAsString) {
        this.chunk_id_group = chunkIdGroup;

        this.modifierTypes = new ArrayList<>();
        for(String strModifierType : modifierTypesAsString.split("\\|")) {
            if(strModifierType.trim().isEmpty()) {
                continue;
            }
            this.modifierTypes.add(ChunkModifierType.getTypeFromString(strModifierType));
        }
    }

    public int getChunkIdGroup() {
        return chunk_id_group;
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
