package org.ardal.commands.npc;

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

public class CreateNpc implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            return false;
        }

        CustomNpcType type = CustomNpcType.getNpcTypeByName(argv.get(0));
        if(type == null) {
            sender.sendMessage("Invalid npc type");
            return true;
        }

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        if(customNPCManager.getAllNpcNames().contains(argv.get(1))){
            sender.sendMessage("Already npc registered with the same name.");
            return true;
        }

        customNPCManager.createNewNpc(argv.get(0), type);
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty() || argv.size() > 2){
            return new ArrayList<>();
        }

        List<String> npcTypes = new ArrayList<>();
        for(CustomNpcType type : CustomNpcType.values()){
            npcTypes.add(type.toString());
        }

        return TabCompleteUtils.getTabCompleteFromStrList(npcTypes, argv.get(0));
    }

    public String getHelp() {
        return String.format("%s%s:%s create a new npc template.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "create";
    }
}
