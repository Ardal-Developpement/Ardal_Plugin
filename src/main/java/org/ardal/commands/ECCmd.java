package org.ardal.commands;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ECCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player;
        if(argv.isEmpty()) {
            player = (Player) sender;
        } else {
            OfflinePlayer offlinePlayer = BukkitUtils.getOfflinePlayerFromName(argv.get(0));

            if(offlinePlayer == null){
                player = null;
            } else {
                player = offlinePlayer.getPlayer();
            }
        }


        if(player == null){
            sender.sendMessage("Invalid player name.");
        } else {
            player.openInventory(player.getEnderChest());
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv.get(0));
    }

    public String getHelp() {
        return String.format("%s%s:%s open enderchest.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "ec";
    }
}
