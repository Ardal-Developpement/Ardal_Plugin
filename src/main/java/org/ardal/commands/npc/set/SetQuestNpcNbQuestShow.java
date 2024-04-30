package org.ardal.commands.npc.set;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.exceptions.NpcNotFound;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.objects.NpcObj;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SetQuestNpcNbQuestShow implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2) {
            return false;
        }

        String uuid = argv.get(0);
        int nbQuestShow = Integer.parseInt(argv.get(1));

        try {
            NpcObj npc = NpcObj.getNpc(uuid);
            if (!(npc instanceof QuestNpc)) {
                sender.sendMessage("This is not a quest npc.");
                return true;
            }

            QuestNpc questNpc = (QuestNpc) npc;
            questNpc.setNbQuestShow(nbQuestShow);
        } catch (NpcNotFound e) {
            sender.sendMessage("Npc not found.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s set the number of quest show at most.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "nbQuestShow";
    }
}