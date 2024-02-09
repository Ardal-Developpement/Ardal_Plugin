package org.ardal.commands.playerinfo.get;


import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GetAdventureLevel implements ArdalCmd {
    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        if(argv.isEmpty()){
            player.sendMessage("Invalid command format.");
            return;
        }
        
        PlayerInfoManager pIM = Ardal.getInstance().getManager(PlayerInfoManager.class);
        OfflinePlayer offlinePlayer = BukkitUtils.playerNameToOfflinePlayer(argv.get(0));
        if(offlinePlayer == null){
            player.sendMessage("Player not found.");
            return;
        }

        player.sendMessage("Your current adventure level is: " + pIM.getAdventureLevel(offlinePlayer));
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv);
    }

    @Override
    public String getHelp() {
        return getCmdName() + " -> get your adventure level.";
    }

    @Override
    public String getCmdName() {
        return "adventureLevel";
    }
}
