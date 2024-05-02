package org.ardal.models;

import org.ardal.Ardal;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class MPlayer {
    private String uuid;
    private String name;
    private int adventure_level;
    private int adventure_xp;
    private Date quest_cooldown;
    public MPlayer(String uuid, String name, int adventure_level, int adventure_xp, @Nullable Date quest_cooldown) {
        this.uuid = uuid;
        this.name = name;
        this.adventure_level = adventure_level;
        this.adventure_xp = adventure_xp;
        this.quest_cooldown = quest_cooldown;
    }

    public boolean updatePlayer(){
        return Ardal.getInstance().getDb().gettPlayer().updatePlayer(this);
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

    public void setAdventureLevel(int adventure_level) {
        this.adventure_level = adventure_level;
    }

    @Nullable
    public Date getQuestCooldown() {
        if(this.quest_cooldown != null && this.quest_cooldown.before(new Date())) {
            this.setQuestCooldown(null);
            this.updatePlayer();
        }

        return quest_cooldown;
    }

    public void setQuestCooldown(Date quest_cooldown) {
        this.quest_cooldown = quest_cooldown;
    }

    public int getAdventureXp() {
        return adventure_xp;
    }

    public void setAdventureXp(int adventure_xp) {
        this.adventure_xp = adventure_xp;
    }
}
