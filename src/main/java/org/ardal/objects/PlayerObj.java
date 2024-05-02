package org.ardal.objects;

import org.ardal.Ardal;
import org.ardal.api.players.PlayerInfo;
import org.ardal.db.Database;
import org.ardal.db.tables.TQuestPlayer;
import org.ardal.models.MPlayer;
import org.ardal.models.MQuest;
import org.ardal.models.pivot.MQuestPlayer;
import org.ardal.utils.DateUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.*;

public class PlayerObj implements PlayerInfo {
    private final MPlayer mPlayer;

    public PlayerObj(MPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    public PlayerObj(String playerUuid) {
        this.mPlayer = Ardal.getInstance().getDb().gettPlayer().getPlayerByUUID(playerUuid);
    }

    public PlayerObj(Player player) {
        this.mPlayer = Ardal.getInstance().getDb().gettPlayer().getPlayerByUUID(player.getUniqueId().toString());
    }

    public PlayerObj(OfflinePlayer player) {
        this.mPlayer = Ardal.getInstance().getDb().gettPlayer().getPlayerByUUID(player.getUniqueId().toString());
    }

    @Override
    public String getUuid() {
        return this.mPlayer.getUuid();
    }

    @Override
    public String getName() {
        return this.mPlayer.getName();
    }

    @Override
    public int getAdventureLevel() {
        return this.mPlayer.getAdventureLevel();
    }

    @Override
    public int getAdventureXp() {
        return this.mPlayer.getAdventureXp();
    }

    @Override
    public int getQuestCooldown() {
        if(this.mPlayer.getQuestCooldown() == null){
            return 0;
        }

        return DateUtils.getMinutesDiff(new Date(), this.mPlayer.getQuestCooldown());
    }

    public List<MQuest> getPlayerQuestObj(boolean isFinished) {
        Database db = Ardal.getInstance().getDb();
        List<Integer> questIds = db.gettQuestPlayer().getQuestIdsByPlayerUuid(this.getUuid(), isFinished);
        List<MQuest> questObjs = new ArrayList<>();
        for(Integer id : questIds) {
            MQuest mQuest = db.gettQuest().getQuestById(id, false);
            if(mQuest != null) {
                questObjs.add(mQuest);
            }
        }

        return questObjs;
    }

    @Override
    public List<String> getPlayerActiveQuestNames() {
        List<String> questNames = new ArrayList<>();
        for(MQuest mQuest : this.getPlayerQuestObj(false)){
            questNames.add(mQuest.getName());
        }

        return questNames;
    }

    @Override
    public List<Integer> getPlayerFinishedQuestIds() {
        List<Integer> questIds = new ArrayList<>();
        for(MQuest mQuest : this.getPlayerQuestObj(true)){
            questIds.add(mQuest.getId());
        }

        return questIds;
    }

    @Override
    public List<Integer> getPlayerActiveQuestIds() {
        List<Integer> questIds = new ArrayList<>();
        for(MQuest mQuest : this.getPlayerQuestObj(false)){
            questIds.add(mQuest.getId());
        }

        return questIds;
    }

    @Override
    public List<String> getPlayerFinishedQuestNames() {
        List<String> questNames = new ArrayList<>();
        for(MQuest mQuest : this.getPlayerQuestObj(true)){
            questNames.add(mQuest.getName());
        }

        return questNames;
    }

    @Override
    public boolean setAdventureLevel(int level) {
        this.mPlayer.setAdventureLevel(level);
        return this.mPlayer.updatePlayer();
    }

    @Override
    public boolean setAdventureXp(int xp) {
        this.mPlayer.setAdventureXp(xp);
        return this.mPlayer.updatePlayer();
    }

    private int nextAdventureXpLevelUp;

    @Override
    public boolean addAdventureXp(int xp) {
        int newXpValue = this.mPlayer.getAdventureXp() + xp;
        if(newXpValue >= this.nextAdventureXpLevelUp) {
            this.setAdventureXp(newXpValue - this.nextAdventureXpLevelUp);
            // TODO increment adventure level of the player
        }

        return this.mPlayer.updatePlayer();
    }

    @Override
    public boolean setQuestCooldown(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        this.mPlayer.setQuestCooldown(timestamp);

        return this.mPlayer.updatePlayer();
    }

    @Override
    public boolean clearQuestCooldown() {
        Ardal.getInstance().getDb().gettPlayer().setQuestCooldown(
                this.getUuid(),
                null
        );

        return this.mPlayer.updatePlayer();
    }

    private boolean addPlayerQuest(String questName, boolean isFinished) {
        Database db = Ardal.getInstance().getDb();
        Integer questId = db.gettQuest().getQuestIdByName(questName, false);
        if(questId == null) {
            return false;
        }

        TQuestPlayer tQuestPlayer = db.gettQuestPlayer();

        if(!tQuestPlayer.setIsFinishedQuestPlayer(questId, this.getUuid(), isFinished)) {
            MQuestPlayer mQuestPlayer = new MQuestPlayer(
                    questId,
                    this.getUuid(),
                    isFinished,
                    new Date()
            );

            return tQuestPlayer.saveQuestPlayer(mQuestPlayer);
        }

        return true;
    }

    @Override
    public boolean addPlayerActiveQuest(String questName) {
        return this.addPlayerQuest(questName, false);
    }

    @Override
    public boolean addPlayerFinishedQuest(String questName) {
        return this.addPlayerQuest(questName, true);
    }

    @Override
    public boolean removeQuest(String questName) {
        Database db = Ardal.getInstance().getDb();
        Integer questId = db.gettQuest().getQuestIdByName(questName, true);
        if(questId == null) {
            return false;
        }

        return db.gettQuestPlayer().removeQuestPlayer(questId, this.getUuid());
    }
}
