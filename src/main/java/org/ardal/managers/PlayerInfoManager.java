package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.players.PlayerInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.playerinfo.get.GetPlayerInfoManager;
import org.ardal.db.PlayerInfoDB;
import org.ardal.listener.PlayerJoinListener;
import org.ardal.objects.PlayerInfoObj;
import org.ardal.utils.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInfoManager extends ArdalCmdManager implements PlayerInfo, ArdalManager {
    private final PlayerInfoDB playerInfoDB;

    public PlayerInfoManager(){
        super(BaseCmdAlias.BASE_PLAYER_INFO_CMD_ALIAS);

        this.registerCmd(new GetPlayerInfoManager());

        this.playerInfoDB = new PlayerInfoDB(Ardal.getInstance().getDataFolder().toPath().toAbsolutePath());
        Ardal.getInstance().getServer().getPluginManager().registerEvents(new PlayerJoinListener(this.playerInfoDB), Ardal.getInstance());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable(){
        this.playerInfoDB.saveDB();
    }

    public PlayerInfoObj getPlayerInfo(OfflinePlayer player){
        if(player.getName() == null) { return null; }
        return this.playerInfoDB.getPlayerInfo(player.getUniqueId());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command can be only used by player.");
            return true;
        }

        return this.onSubCmd(commandSender, command, s, StringUtils.getStrListFromStrArray(strings));
    }

    @Override
    public boolean addAdventureLevel(OfflinePlayer offlinePlayer, long l) {
        if(offlinePlayer.getName() == null){
            return false;
        }

        PlayerInfoObj player = this.getPlayerInfo(offlinePlayer);
        if(player == null) { return false; }

        player.addAdventureLevel(l);
        return true;
    }

    @Override
    public long getAdventureLevel(OfflinePlayer offlinePlayer) {
        if(offlinePlayer.getName() == null){
            return 0;
        }

        PlayerInfoObj player = this.getPlayerInfo(offlinePlayer);
        return player != null ? player.getAdventureLevel() : 0;
    }

    @Override
    public List<UUID> getPlayerActiveQuests(OfflinePlayer offlinePlayer) {
        if(offlinePlayer.getName() == null){
            return new ArrayList<>();
        }

        PlayerInfoObj player = this.getPlayerInfo(offlinePlayer);
        return player != null ? player.getActiveQuest() : new ArrayList<>();
    }

    @Override
    public List<UUID> getPlayerFinishedQuests(OfflinePlayer offlinePlayer) {
        if(offlinePlayer.getName() == null){
            return new ArrayList<>();
        }

        PlayerInfoObj player = this.getPlayerInfo(offlinePlayer);
        return player != null ? player.getFinishedQuest() : new ArrayList<>();
    }

    @Override
    public boolean addPlayerActiveQuest(OfflinePlayer offlinePlayer, UUID uuid) {
        if(offlinePlayer.getName() == null){
            return false;
        }

        PlayerInfoObj player = this.getPlayerInfo(offlinePlayer);
        return player != null && player.addActiveQuest(uuid);
    }

    @Override
    public boolean removePlayerActiveQuest(OfflinePlayer offlinePlayer, UUID uuid) {
        if(offlinePlayer.getName() == null){
            return false;
        }

        PlayerInfoObj player = this.getPlayerInfo(offlinePlayer);
        return player != null && player.removeActiveQuest(uuid);
    }

    @Override
    public boolean removePlayerFinishedQuest(OfflinePlayer offlinePlayer, UUID uuid) {
        if(offlinePlayer.getName() == null){
            return false;
        }

        PlayerInfoObj player = this.getPlayerInfo(offlinePlayer);
        return player != null && player.removeFinishedQuest(uuid);
    }

    @Override
    public boolean isPlayerRegistered(OfflinePlayer offlinePlayer) {
        return offlinePlayer.getName() != null && this.getPlayerInfo(offlinePlayer) != null;
    }
}
