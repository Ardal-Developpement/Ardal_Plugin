package org.ardal.commands.playerinfo.set;

import org.ardal.api.commands.ArdalCmd;

import org.ardal.objects.PlayerObj;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetPlayerAdventureLevel implements ArdalCmd {
        @Override
        public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
            if(argv.isEmpty()) {
                return false;
            }

            Player player;
            int level;

            if(argv.size() == 1) {
                player = (Player) sender;
                level = Integer.parseInt(argv.get(0));
            } else {
                OfflinePlayer offlinePlayer = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
                if(offlinePlayer == null) {
                    sender.sendMessage("Player not found.");
                    return true;
                }

                player = offlinePlayer.getPlayer();
                level = Integer.parseInt(argv.get(1));
            }

            PlayerObj playerObj = new PlayerObj(player);
            if(playerObj.setAdventureLevel(level)) {
                sender.sendMessage("Successfully set the adventure level to " + level + ".");
            } else {
                sender.sendMessage("Failed to set the adventure level.");
            }

            return true;
        }

        @Override
        public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
            if(argv.size() < 2) {
                return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv);
            }

            return new ArrayList<>();
        }

        @Override
        public String getHelp() {
            return String.format("%s%s:%s set adventure level of a player.",
                    ChatColor.GOLD,
                    getCmdName(),
                    ChatColor.WHITE);
        }

        @Override
        public String getCmdName() {
            return "adventureLevel";
        }
}
