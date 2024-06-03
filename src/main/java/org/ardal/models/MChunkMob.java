package org.ardal.models;

public class MChunkMob {
    private int chunk_id_group;
    private String mob_type;
    private int level;
    private float cooldown;

    public MChunkMob(int chunk_id_group, String mob_type, int level, float cooldown) {
        this.chunk_id_group = chunk_id_group;
        this.mob_type = mob_type;
        this.level = level;
        this.cooldown = cooldown;
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
}
