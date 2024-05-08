package org.ardal.commands.playerinfo.set;

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

import java.util.ArrayList;
import java.util.List;

public class SetPlayerAdventureXp implements ArdalCmd {
        @Override
        public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
            if(argv.isEmpty()) {
                return false;
            }

            Player player;
            int xp;

            if(argv.size() == 1) {
                player = (Player) sender;
                xp = Integer.parseInt(argv.get(0));
            } else {
                OfflinePlayer offlinePlayer = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
                if(offlinePlayer == null) {
                    sender.sendMessage("Player not found.");
                    return true;
                }

                player = offlinePlayer.getPlayer();
                xp = Integer.parseInt(argv.get(1));
            }

            if(xp < 0) {
                sender.sendMessage("You have to specify a positive number!");
                return true;
            }

            PlayerObj playerObj = Ardal.getInstance().getManager(PlayerInfoManager.class).getPlayerObj(player);;
            if(playerObj.addAdventureXp(xp - playerObj.getAdventureXp(), player)) {
                sender.sendMessage("Successfully set the adventure xp to " + xp + ".");
            } else {
                sender.sendMessage("Failed to set the adventure xp.");
            }

            return true;
        }

        @Override
        public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
            if(argv.size() < 2) {
                return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNames(), argv);
            }

            return new ArrayList<>();
        }

        @Override
        public String getHelp() {
            return String.format("%s%s:%s set adventure xp of a player.",
                    ChatColor.GOLD,
                    getCmdName(),
                    ChatColor.WHITE);
        }

        @Override
        public String getCmdName() {
            return "adventureXp";
        }
}
