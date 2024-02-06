package org.ardal.commands.quests;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.commands.QuestCmd;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetStateQuest implements QuestCmd {
    @Override
    public void execute(Ardal plugin, Player player, Command command, String s, List<String> argv) {
        String questName = StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()));
        JsonObject questObj = plugin.getQuestManager().getQuestDB().getQuest(questName);

        if(questObj == null){
            player.sendMessage("Unknown quest name.");
            return;
        }

        boolean state = argv.get(0).toLowerCase().trim().equals("true");
        questObj.addProperty("isActive", state);
        plugin.getQuestManager().getQuestDB().saveDB();

        player.sendMessage("Set quest " + questName + " visibility to " + state);
    }

    @Override
    public List<String> getTabComplete(Ardal plugin, CommandSender player, Command command, String s, List<String> argv) {
        if(argv.size() == 1){
            return TabCompleteUtils.getTabCompleteForBool(argv.get(0));
        }

        return TabCompleteUtils.getTabCompleteForQuestName(plugin.getQuestManager().getQuestDB(), argv.subList(1, argv.size()));
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [true|false] [quest name]";
    }

    @Override
    public String getCmdName() {
        return "setState";
    }
}
