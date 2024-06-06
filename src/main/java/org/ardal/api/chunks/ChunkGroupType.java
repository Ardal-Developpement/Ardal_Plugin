package org.ardal.api.chunks;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ChunkGroupType {
    NONE,
    MOB;

    @Nullable
    public static ChunkGroupType getTypeFromString(String strType) {
        for(ChunkGroupType type : ChunkGroupType.values()) {
            if(type.toString().equals(strType)) {
                return type;
            }
        }

        return null;
    }

    public static List<String> getTypesAsList() {
        return Arrays.stream(ChunkGroupType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}
