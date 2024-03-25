package org.ardal.commands.playerinfo.remove;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.ardal.objects.PlayerObj;
import org.ardal.objects.QuestObj;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RemoveActiveQuestCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            return false;
        }

        OfflinePlayer player = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
        if(player == null) {
            sender.sendMessage("Invalid player name.");
            return true;
        }

        String questName = StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()));
        QuestObj questObj = new QuestObj(questName);
        PlayerObj playerObj = new PlayerObj(player);


        if(!questObj.isQuestExist()){
            sender.sendMessage("Invalid quest name.");
            return true;
        }

        if(!playerObj.getPlayerActiveQuestNames().contains(questName)){
            sender.sendMessage("Player don't have this active quest.");
            return true;
        }

        playerObj.removeQuest(questName);
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        if(argv.size() == 1){
            return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv);
        }

        OfflinePlayer player = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
        if(player == null) { return new ArrayList<>(); }


        return TabCompleteUtils.getTabCompleteFromStrList(
                new PlayerObj(player).getPlayerActiveQuestNames(),
                StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()))
        );
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s remove an active quest to a player.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "activeQuest";
    }
}
