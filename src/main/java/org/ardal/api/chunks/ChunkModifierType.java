package org.ardal.api.chunks;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ChunkModifierType {
    MOB;

    @Nullable
    public static ChunkModifierType getTypeFromString(String strType) {
        for(ChunkModifierType type : ChunkModifierType.values()) {
            if(type.toString().equals(strType)) {
                return type;
            }
        }

        return null;
    }

    public static List<String> getTypesAsList() {
        return Arrays.stream(ChunkModifierType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}
