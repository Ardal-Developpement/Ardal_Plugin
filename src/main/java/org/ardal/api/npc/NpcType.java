package org.ardal.api.npc;

import org.jetbrains.annotations.Nullable;

public enum NpcType {
    QUEST_NPC;

    @Nullable
    public static NpcType getNpcTypeByName(String typeName){
        for(NpcType type : NpcType.values()){
            if(type.toString().equals(typeName)){
                return type;
            }
        }

        return null;
    }
}
