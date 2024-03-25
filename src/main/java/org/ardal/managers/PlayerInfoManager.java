package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.players.PlayerManagerInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.playerinfo.add.AddPlayerInfoManager;
import org.ardal.commands.playerinfo.list.ListPlayerInfoManager;
import org.ardal.commands.playerinfo.remove.RemovePlayerInfoManager;
import org.ardal.models.MPlayer;
import org.ardal.objects.PlayerObj;
import org.ardal.utils.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayerInfoManager extends ArdalCmdManager implements PlayerManagerInfo, ArdalManager, Listener {
    public PlayerInfoManager(){
        super(BaseCmdAlias.BASE_PLAYER_INFO_CMD_ALIAS);

        this.registerCmd(new AddPlayerInfoManager());
        this.registerCmd(new RemovePlayerInfoManager());
        this.registerCmd(new ListPlayerInfoManager());

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

            Ardal.getInstance().getDb().gettPlayer().createPlayer(mPlayer);
        }
    }



    public boolean isPlayerRegistered(OfflinePlayer player) {
        return Ardal.getInstance().getDb().gettPlayer().isPlayerExistByUuid(player.getUniqueId().toString());
    }

    @Override
    public boolean getIsPlayerExist(OfflinePlayer player) {
        return player.getName() != null
                || Ardal.getInstance().getDb().gettPlayer().isPlayerExistByUuid(player.getUniqueId().toString());
    }

    @Override
    public List<String> getAllPlayerNames() {
        return Ardal.getInstance().getDb().gettPlayer().getPlayerNames();
    }

    @Override
    public boolean saveNewPlayer(OfflinePlayer player) {
        if(player.getName() == null) { return false; }
        return Ardal.getInstance().getDb().gettPlayer().createPlayer(
                new MPlayer(
                        player.getUniqueId().toString(),
                        player.getName(),
                        0,
                        null
                ));
    }
}
