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
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        if(argv.isEmpty()){
            player.sendMessage("Invalid command format.");
            return;
        }

        String questName = StringUtils.getStringFromConcatStringList(argv);

        if(Ardal.getInstance().getManager(QuestManager.class).removeQuest(questName)){
            player.sendMessage("Success to remove quest: " + questName);
        } else {
            player.sendMessage("Unknown quest name.");
        }
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(Ardal.getInstance().getManager(QuestManager.class).getQuestDB().getKeySet(), argv);
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
