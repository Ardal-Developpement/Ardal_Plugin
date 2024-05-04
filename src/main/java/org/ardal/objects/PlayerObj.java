package org.ardal.objects;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.ardal.Ardal;
import org.ardal.api.players.PlayerInfo;
import org.ardal.db.Database;
import org.ardal.db.tables.TQuestPlayer;
import org.ardal.managers.AdventureLevelManager;
import org.ardal.models.MAdventureLevel;
import org.ardal.models.MPlayer;
import org.ardal.models.MQuest;
import org.ardal.models.pivot.MQuestPlayer;
import org.ardal.utils.ChatUtils;
import org.ardal.utils.DateUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlayerObj implements PlayerInfo {
    private final MPlayer mPlayer;

    private int nextAdventureXpLevelUp;

    public PlayerObj(MPlayer mPlayer) {
        this.mPlayer = mPlayer;

        MAdventureLevel nextLevel = Ardal.getInstance().getManager(AdventureLevelManager.class)
                .getNextAdventureLevel(this.getAdventureLevel());

        if(nextLevel == null) {
            this.nextAdventureXpLevelUp = -1;
        } else {
            this.nextAdventureXpLevelUp = nextLevel.getLevel();
        }
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

    @Override
    public boolean addAdventureXp(int xp, Player player) {
        this.addXpOnActionBar(xp, player);
        int newXpValue = this.mPlayer.getAdventureXp() + xp;
        System.out.println("test2");
        if(nextAdventureXpLevelUp > 0 &&  newXpValue >= this.nextAdventureXpLevelUp) {
            this.setAdventureXp(newXpValue - this.nextAdventureXpLevelUp);
            MAdventureLevel nextLevel = Ardal.getInstance().getManager(AdventureLevelManager.class)
                    .getNextAdventureLevel(this.getAdventureLevel());

            if(nextLevel == null) {
                System.out.println("test4");
                this.nextAdventureXpLevelUp = -1;
            } else {
                System.out.println("test5");
                this.setAdventureLevel(nextLevel.getLevel());
                this.nextAdventureXpLevelUp = nextLevel.getLevel();
            }

            System.out.println("test6");


            return this.mPlayer.updatePlayer();
        }

        return true;
    }

    private long lastActionBarTime = 0;
    private long timeBeforeResetActionBarXp = 3000;
    private int currentXpAdd = 0;

    private void addXpOnActionBar(int xp, Player player) {
        if (System.currentTimeMillis() > this.lastActionBarTime + this.timeBeforeResetActionBarXp) {
            this.currentXpAdd = 0;
        }

        this.currentXpAdd += xp;

        TextComponent part1 = new TextComponent("+ ");
        TextComponent part2 = new TextComponent(String.valueOf(this.currentXpAdd));
        TextComponent part3 = new TextComponent(" xp");
        part1.setColor(ChatColor.AQUA);
        part2.setColor(ChatColor.GREEN);
        part3.setColor(ChatColor.AQUA);

        ChatUtils.sendActionBar(player, new TextComponent(part1, part2, part3));

        this.lastActionBarTime = System.currentTimeMillis();
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
