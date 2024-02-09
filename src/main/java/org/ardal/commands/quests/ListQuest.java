package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.objects.QuestObj;
import org.ardal.managers.QuestManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListQuest implements ArdalCmd {
    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        List<QuestObj> questObjs = Ardal.getInstance().getManager(QuestManager.class).getAllQuestObj(player);

        if(questObjs.isEmpty()){
            player.sendMessage("No quest found.");
            return;
        }

        player.sendMessage("Found " + questObjs.size() + " quests:\n");

        for(int i = 0; i < questObjs.size(); i++){
            player.sendMessage("Quest " + (i + 1) + ": " + questObjs.get(i).getQuestName());
        }
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
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
