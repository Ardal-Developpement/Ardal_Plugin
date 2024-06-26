package org.ardal.commands.individual;


import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.objects.PlayerObj;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdventureXp implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
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

        PlayerObj playerObj = Ardal.getInstance().getManager(PlayerInfoManager.class).getPlayerObj(player);
        player.sendMessage("Your current adventure xp is: " + playerObj.getAdventureXp());
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNames(), argv);
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s get adventure xp of the player (or of itself).",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "adventureXp";
    }
}
