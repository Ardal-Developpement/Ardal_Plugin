package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveQuest implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return false;
        }

        Player player = (Player) sender;
        String questName = StringUtils.getStringFromConcatStringList(argv);/*
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);

        if(!questManager.questExist(questName)){
            player.sendMessage("Unknown quest name.");
            return true;
        }

        if(questManager.removeQuest(questName)){
            player.sendMessage("Success to remove quest: " + questName);
        } else {
            player.sendMessage("Failed to remove quest: " + questName);
        }
*/
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        return TabCompleteUtils.getTabCompleteFromStrList(questManager.getAllQuestNames(false), argv);
    }

    public String getHelp() {
        return String.format("%s%s:%s remove quest by his name.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "remove";
    }
}
