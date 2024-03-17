package org.ardal.models;

import java.util.Date;
import java.util.UUID;

public class MPlayer {
    private UUID uuid;
    private String name;
    private int adventure_level;
    private Date quest_cooldown;
    public MPlayer(UUID uuid, String name, int adventure_level, Date quest_cooldown) {
        this.uuid = uuid;
        this.name = name;
        this.adventure_level = adventure_level;
        this.quest_cooldown = quest_cooldown;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdventure_level() {
        return adventure_level;
    }

    public void setAdventure_level(int adventure_level) {
        this.adventure_level = adventure_level;
    }

    public Date getQuest_cooldown() {
        return quest_cooldown;
    }

    public void setQuest_cooldown(Date quest_cooldown) {
        this.quest_cooldown = quest_cooldown;
    }
}
