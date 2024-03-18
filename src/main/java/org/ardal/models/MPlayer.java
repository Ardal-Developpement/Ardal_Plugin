package org.ardal.models;

import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class MPlayer {
    private String uuid;
    private String name;
    private int adventure_level;
    private Date quest_cooldown;
    public MPlayer(String uuid, String name, int adventure_level, @Nullable Date quest_cooldown) {
        this.uuid = uuid;
        this.name = name;
        this.adventure_level = adventure_level;
        this.quest_cooldown = quest_cooldown;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdventureLevel() {
        return adventure_level;
    }

    public void setAdventure_level(int adventure_level) {
        this.adventure_level = adventure_level;
    }

    public Date getQuestCooldown() {
        return quest_cooldown;
    }

    public void setQuest_cooldown(Date quest_cooldown) {
        this.quest_cooldown = quest_cooldown;
    }
}
