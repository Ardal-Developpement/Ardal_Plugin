package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveQuest implements ArdalCmd {
    @Override
    public void execute(Ardal plugin, Player player, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            player.sendMessage("Invalid command format.");
            return;
        }

        String questName = StringUtils.getStringFromConcatStringList(argv);


        if(plugin.getManager(QuestManager.class).getQuestDB().removeQuestBook(questName)){
            player.sendMessage("Success to remove quest book: " + questName);
        } else {
            player.sendMessage("Unknown quest name.");
        }
    }

    @Override
    public List<String> getTabComplete(Ardal plugin, CommandSender sender, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteForQuestName(plugin.getManager(QuestManager.class).getQuestDB(), argv);
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [quest name] -> remove quest by his name";
    }

    @Override
    public String getCmdName() {
        return "remove";
    }
}
