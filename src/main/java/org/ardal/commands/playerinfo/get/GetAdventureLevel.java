package org.ardal.commands.playerinfo.get;


import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GetAdventureLevel implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return false;
        }

        Player player;
        if(argv.isEmpty()){
            player = (Player) sender;
        } else{
            OfflinePlayer offlinePlayer = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
            if(offlinePlayer == null){
                sender.sendMessage("Player not found.");
                return true;
            }

            player = offlinePlayer.getPlayer();
        }

        PlayerInfoManager pIM = Ardal.getInstance().getManager(PlayerInfoManager.class);
        player.sendMessage("Your current adventure level is: " + pIM.getAdventureLevel(player));
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv);
    }

    @Override
    public String getHelp() {
        return String.format("%s%s [player]:%s get adventure level of the player (or of itself).",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "adventureLevel";
    }
}
