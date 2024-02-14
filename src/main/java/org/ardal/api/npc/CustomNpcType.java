package org.ardal.api.npc;

import org.jetbrains.annotations.Nullable;

public enum CustomNpcType {
    QUEST_NPC;

    @Nullable
    public static CustomNpcType getNpcTypeByName(String typeName){
        for(CustomNpcType type : CustomNpcType.values()){
            if(type.toString().equals(typeName)){
                return type;
            }
        }

        return null;
    }
}
