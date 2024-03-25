package org.ardal.commands.quests.get;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GetQuestSynopsis implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return false;
        }

        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        String questName = StringUtils.getStringFromConcatStringList(argv);
    /*
        String synopsis  = questManager.getQuestSynopsis(questName);
        if(synopsis == null) {
            sender.sendMessage("Invalid quest name.");
            return true;
        }

        sender.sendMessage("Synopsis of '" + questName + "': " + synopsis);*/
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        return TabCompleteUtils.getTabCompleteFromStrList(questManager.getAllQuestNames(false), StringUtils.getStringFromConcatStringList(argv));
    }

    public String getHelp() {
        return String.format("%s%s:%s get synopsis.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "synopsis";
    }
}
