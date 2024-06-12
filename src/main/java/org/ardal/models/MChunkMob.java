package org.ardal.models;

import org.ardal.Ardal;
import org.ardal.api.chunks.ChunkModifierType;
import org.ardal.api.entities.mobs.MobType;

import java.util.ArrayList;
import java.util.List;

public class MChunkMob {
    private int chunk_id_group;
    private List<MobType> mob_types;
    private int level;
    private float cooldown;
    private boolean enable;

    public MChunkMob(int chunk_id_group, List<MobType> mobTypes, int level, float cooldown, boolean enable) {
        this.chunk_id_group = chunk_id_group;
        this.mob_types = mob_types;
        this.level = level;
        this.cooldown = cooldown;
        this.enable = enable;
    }

    public MChunkMob(int chunk_id_group, String mob_types, int level, float cooldown, boolean enable) {
        this.chunk_id_group = chunk_id_group;
        this.mob_types = new ArrayList<>();
        this.level = level;
        this.cooldown = cooldown;
        this.enable = enable;

        for(String strMobType : mob_types.split("\\|")) {
            if(!strMobType.trim().isEmpty()) {
                this.mob_types.add(MobType.getMobTypeByName(strMobType));
            }
        }
    }

    public boolean updateChunkMob(){
        return Ardal.getInstance().getDb().gettChunkMob().updateChunkMob(this);
    }

    public void addSpawningMob(MobType mobType) {
        this.mob_types.add(mobType);
    }

    public void removeSpawningMob(MobType mobType) {
        this.mob_types.remove(mobType);
    }

    public int getChunkIdGroup() {
        return chunk_id_group;
    }

    public void setChunkIdGroup(int chunk_id_group) {
        this.chunk_id_group = chunk_id_group;
    }

    public String getMobTypesAsString() {
        StringBuilder sb = new StringBuilder();
        for(MobType mobType : this.mob_types) {
            sb.append(mobType.toString()).append('|');
        }

        return sb.toString();
    }

    public List<MobType> getMobTypes() {
        return this.mob_types;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel(int levelAdd) {
        this.level += levelAdd;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public void addCooldown(float cooldownAdd) {
        this.cooldown += cooldownAdd;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
