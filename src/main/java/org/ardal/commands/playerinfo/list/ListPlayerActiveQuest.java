package org.ardal.commands.playerinfo.list;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListPlayerActiveQuest implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        OfflinePlayer player;
        if(argv.isEmpty()){
            player = (OfflinePlayer) sender;
        } else {
            player = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
            if(player == null) {
                sender.sendMessage("Invalid player name.");
                return true;
            }
        }
/*
        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);
        List<String> activeQuests = playerInfoManager.getPlayerActiveQuests(player);
        sender.sendMessage("Found " + activeQuests.size() + " active quests:");

        int i = 1;
        for(String questName : activeQuests){
            sender.sendMessage(i++ + ": " + questName);
        }*/

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv.get(0));
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s get active quests of the player (or of itself).",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "activeQuest";
    }
}
