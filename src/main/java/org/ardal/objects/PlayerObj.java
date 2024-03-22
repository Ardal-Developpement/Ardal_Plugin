package org.ardal.objects;

import org.ardal.Ardal;
import org.ardal.api.players.PlayerInfo;
import org.ardal.db.Database;
import org.ardal.db.tables.TQuestPlayer;
import org.ardal.models.MPlayer;
import org.ardal.models.MQuest;
import org.ardal.models.pivot.MQuestPlayer;
import org.ardal.utils.DateUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlayerObj implements PlayerInfo {
    private final MPlayer mPlayer;

    public PlayerObj(MPlayer mPlayer) {
        this.mPlayer = mPlayer;
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
    public int getQuestCooldown() {
        if(this.mPlayer.getQuestCooldown() == null){
            return 0;
        }

        return DateUtils.getMinutesDiff(new Date(), this.mPlayer.getQuestCooldown());
    }

    public List<String> getPlayerQuests(boolean isFinished) {
        Database db = Ardal.getInstance().getDb();
        List<Integer> questIds = db.gettQuestPlayer().getQuestsIdByPlayerUuid(this.getUuid(), isFinished);
        List<String> questNames = new ArrayList<>();
        for(Integer id : questIds) {
            MQuest mQuest = db.gettQuest().getQuestById(id);
            if(mQuest != null) {
                questNames.add(mQuest.getName());
            }
        }

        return questNames;
    }

    @Override
    public List<String> getPlayerActiveQuests() {
        return this.getPlayerQuests(false);
    }

    @Override
    public List<String> getPlayerFinishedQuests() {
        return this.getPlayerQuests(true);
    }

    @Override
    public boolean setAdventureLevel(int level) {
        this.mPlayer.setAdventure_level(level);
        return this.mPlayer.updatePlayer();
    }

    @Override
    public boolean setQuestCooldown(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

        Ardal.getInstance().getDb().gettPlayer().setQuestCooldown(
                this.getUuid(),
                timestamp
        );

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
