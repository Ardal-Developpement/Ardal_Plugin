package org.ardal.api.entities.mobs;

import org.ardal.entities.mobs.CustomMob;
import org.ardal.entities.mobs.type.CustomSkeleton;
import org.jetbrains.annotations.Nullable;

public enum MobType {
    SKELETON(CustomSkeleton.class);

    private final Class<? extends CustomMob> mobClass;

    MobType(Class<? extends CustomMob> mobClass) {
        this.mobClass = mobClass;
    }

    public Class<? extends CustomMob> getMobClass() {
        return mobClass;
    }

    @Nullable
    public static MobType getMobTypeByName(String name) {
        for (MobType mobType : MobType.values()) {
            if(mobType.toString().equals(name)) {
                return mobType;
            }
        }

        return null;
    }
}
