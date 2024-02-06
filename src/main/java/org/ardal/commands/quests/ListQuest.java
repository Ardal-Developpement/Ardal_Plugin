package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.QuestCmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListQuest implements QuestCmd {
    @Override
    public void execute(Ardal plugin, Player player, Command command, String s, List<String> argv) {
        List<String> questList = plugin.getQuestManager().getQuestDB().getAllQuestName();

        if(questList.isEmpty()){
            player.sendMessage("No quest found.");
            return;
        }

        player.sendMessage("Found " + questList.size() + " quests:\n");

        for(int i = 0; i < questList.size(); i++){
            player.sendMessage("Quest " + (i + 1) + ": " + questList.get(i));
        }
    }

    @Override
    public List<String> getTabComplete(Ardal plugin, CommandSender player, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return getCmdName() + " -> list all the quest";
    }

    @Override
    public String getCmdName() {
        return "list";
    }
}
