package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListQuest implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        List<String> questObjs = Ardal.getInstance().getManager(QuestManager.class).getAllQuestNames(false);

        if(questObjs.isEmpty()){
            player.sendMessage("No quest found.");
            return true;
        }

        player.sendMessage("Found " + questObjs.size() + " quests:\n");

        for(int i = 0; i < questObjs.size(); i++){
            player.sendMessage("Quest " + (i + 1) + ": " + questObjs.get(i));
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    public String getHelp() {
        return String.format("%s%s:%s list all the quest.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "list";
    }
}
