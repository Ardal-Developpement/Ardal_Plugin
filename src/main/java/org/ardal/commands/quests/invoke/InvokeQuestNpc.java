package org.ardal.commands.quests.invoke;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.managers.CustomNPCManager;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class InvokeQuestNpc implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return false;
        }

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        if(!customNPCManager.getAllNpcNamesByType(CustomNpcType.QUEST_NPC).contains(argv.get(0))){
            sender.sendMessage("Invalid npc name.");
            return true;
        }

        if(customNPCManager.invokeNpc(argv.get(0))){
            sender.sendMessage("Success to invoke: " + argv.get(0));
        } else {
            sender.sendMessage("Failed to invoke npc.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        return TabCompleteUtils.getTabCompleteFromStrList(customNPCManager.getAllNpcNamesByType(CustomNpcType.QUEST_NPC), argv.get(0));
    }

    public String getHelp() {
        return String.format("%s%s:%s invoke quest npc.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "npc";
    }
}
