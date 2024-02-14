package org.ardal.commands.npc;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.CustomNPCManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DeleteNpc implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return false;
        }

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        if(customNPCManager.getAllNpcNames().contains(argv.get(0))){
            customNPCManager.deleteNpc(argv.get(0));
            sender.sendMessage("Success to delete npc template.");
        } else {
            sender.sendMessage("Invalid npc name.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        return customNPCManager.getAllNpcNames();
    }

    public String getHelp() {
        return String.format("%s%s:%s delete a npc template",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "delete";
    }
}
