package org.ardal.models;

import org.ardal.Ardal;
import org.ardal.api.entities.mobs.MobType;

import java.util.ArrayList;
import java.util.List;

public class MChunkMob {
    private int chunk_id_group;
    private String mob_type;
    private int level;
    private float cooldown;
    private boolean enable;

    public MChunkMob(int chunk_id_group, String mob_type, int level, float cooldown, boolean enable) {
        this.chunk_id_group = chunk_id_group;
        this.mob_type = mob_type;
        this.level = level;
        this.cooldown = cooldown;
        this.enable = enable;
    }

    public boolean updateChunkMob(){
        return Ardal.getInstance().getDb().gettChunkMob().updateChunkMob(this);
    }

    public void addSpawningMob(MobType mobType) {
        this.mob_type += mobType.toString() + "|";
    }

    public void removeSpawningMob(MobType mobType) {
        this.mob_type = this.mob_type.replace(mobType.toString() + "|", "");
    }

    public int getChunkIdGroup() {
        return chunk_id_group;
    }

    public void setChunkIdGroup(int chunk_id_group) {
        this.chunk_id_group = chunk_id_group;
    }

    public String getMobType() {
        return mob_type;
    }

    public List<MobType> getMobTypesAsList() {
        List<MobType> mobTypes = new ArrayList<>();

        for(String e : this.mob_type.split("\\|")) {
            if(!e.isEmpty()) {
                MobType type = MobType.getMobTypeByName(e);
                if(type != null) {
                    mobTypes.add(type);
                }
            }
        }

        return mobTypes;
    }

    public void setMobType(String mob_type) {
        this.mob_type = mob_type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
