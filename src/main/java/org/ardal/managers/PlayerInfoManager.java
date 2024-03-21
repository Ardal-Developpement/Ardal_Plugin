package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.players.PlayerInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.playerinfo.add.AddPlayerInfoManager;
import org.ardal.commands.playerinfo.list.ListPlayerInfoManager;
import org.ardal.commands.playerinfo.remove.RemovePlayerInfoManager;
import org.ardal.db.Database;
import org.ardal.db.tables.TPlayer;
import org.ardal.db.tables.TQuest;
import org.ardal.db.tables.TQuestPlayer;
import org.ardal.models.MPlayer;
import org.ardal.models.MQuest;
import org.ardal.models.pivot.MQuestPlayer;
import org.ardal.utils.DateUtils;
import org.ardal.utils.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlayerInfoManager extends ArdalCmdManager implements PlayerInfo, ArdalManager, Listener {
    private TPlayer tPlayers;
    private TQuestPlayer tQuestPlayer;
    private TQuest tQuests;

    public PlayerInfoManager(){
        super(BaseCmdAlias.BASE_PLAYER_INFO_CMD_ALIAS);

        this.registerCmd(new AddPlayerInfoManager());
        this.registerCmd(new RemovePlayerInfoManager());
        this.registerCmd(new ListPlayerInfoManager());

        Database db = Ardal.getInstance().getDb();

        this.tQuests = db.gettQuest();
        this.tQuestPlayer = db.gettQuestPlayer();
        this.tPlayers = db.gettPlayer();

        Ardal.getInstance().getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }

    @Override
    public void onEnable() { }

    @Override
    public void onDisable(){ }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command can be only used by player.");
            return true;
        }

        return this.onSubCmd(sender, command, s, StringUtils.getStrListFromStrArray(strings));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!this.isPlayerRegistered(player)){
            MPlayer mPlayer = new MPlayer(
                    player.getUniqueId().toString(),
                    player.getName(),
                    0,
                    null);

            this.tPlayers.savePlayer(mPlayer);
        }
    }

    @Override
    public boolean addAdventureLevel(OfflinePlayer player, int level) {
        return player.getName() != null
                && this.tPlayers.setAdventureLevel(player.getUniqueId().toString(), level);
    }

    @Override
    public int getAdventureLevel(OfflinePlayer player) {
        MPlayer mPlayer = this.tPlayers.getPlayerByUUID(player.getUniqueId().toString());
        return mPlayer == null ? -1 : mPlayer.getAdventureLevel();
    }

    public List<String> getPlayerQuests(OfflinePlayer player, boolean isFinished) {
        List<Integer> questIds = this.tQuestPlayer.getQuestsIdByPlayerUuid(player.getUniqueId().toString(), isFinished);
        List<String> questNames = new ArrayList<>();
        for(Integer id : questIds) {
            MQuest mQuest = this.tQuests.getQuestById(id);
            if(mQuest != null) {
                questNames.add(mQuest.getName());
            }
        }

        return questNames;
    }

    @Override
    public List<String> getPlayerActiveQuests(OfflinePlayer player) {
        return this.getPlayerQuests(player, false);
    }

    @Override
    public List<String> getPlayerFinishedQuests(OfflinePlayer player) {
        return this.getPlayerQuests(player, true);
    }

    private boolean addPlayerQuest(OfflinePlayer player, String questName, boolean isFinished) {
        Integer questId = this.tQuests.getQuestIdByName(questName, false);
        if(questId == null || player.getName() == null) {
            return false;
        }

        if(!this.tQuestPlayer.setIsFinishedQuestPlayer(questId, player.getUniqueId().toString(), isFinished)) {
            MQuestPlayer mQuestPlayer = new MQuestPlayer(
                    questId,
                    player.getUniqueId().toString(),
                    isFinished,
                    new Date()
            );

            return this.tQuestPlayer.saveQuestPlayer(mQuestPlayer);
        }

        return true;
    }
    @Override
    public boolean addPlayerActiveQuest(OfflinePlayer player, String questName) {
        return this.addPlayerQuest(player, questName, false);
    }

    @Override
    public boolean addPlayerFinishedQuest(OfflinePlayer player, String questName) {
        return this.addPlayerQuest(player, questName, true);
    }

    @Override
    public boolean removePlayerQuest(OfflinePlayer player, String questName) {
        Integer questId = this.tQuests.getQuestIdByName(questName, true);
        if(questId == null || player.getName() == null) {
            return false;
        }

        return this.tQuestPlayer.removeQuestPlayer(questId, player.getUniqueId().toString());
    }

    @Override
    public boolean isPlayerRegistered(OfflinePlayer player) {
        return player.getName() != null
                || this.tPlayers.isPlayerExistByUuid(player.getUniqueId().toString());
    }

    @Override
    public void setQuestCooldown(OfflinePlayer player, int minutes) {
        if(player.getName() != null){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, minutes);
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

            this.tPlayers.setQuestCooldown(
                    player.getUniqueId().toString(),
                    timestamp
            );
        }
    }

    @Override
    public void clearQuestCooldown(OfflinePlayer player) {
        this.tPlayers.setQuestCooldown(
                player.getUniqueId().toString(),
                null
        );
    }

    @Override
    public int getQuestCooldown(OfflinePlayer player) {
        MPlayer mPlayer = this.tPlayers.getPlayerByUUID(player.getUniqueId().toString());
        if(mPlayer == null || mPlayer.getQuestCooldown() == null){
            return 0;
        }

        return DateUtils.getMinutesDiff(new Date(), mPlayer.getQuestCooldown());
    }
}
