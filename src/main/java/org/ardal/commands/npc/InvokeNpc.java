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

public class InvokeNpc implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            return false;
        }

        CustomNpcType type = CustomNpcType.getNpcTypeByName(argv.get(0));
        if(type == null) {
            sender.sendMessage("Invalid npc type.");
            return true;
        }

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        if(customNPCManager.invokeNpc(argv.get(1))){
            sender.sendMessage("Success to invoke npc.");
        } else {
            sender.sendMessage("Invalid npc name.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() == 1){
            List<String> typeList = new ArrayList<>();

            for(CustomNpcType type : CustomNpcType.values()){
                typeList.add(type.toString());
            }

            return TabCompleteUtils.getTabCompleteFromStrList(typeList, argv.get(0));
        } else if(argv.size() == 2){

            CustomNpcType type = CustomNpcType.getNpcTypeByName(argv.get(0));
            if(type == null) { return new ArrayList<>(); }

            CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
            return TabCompleteUtils.getTabCompleteFromStrList(customNPCManager.getAllNpcNamesByType(type), argv.get(1));
        }

        return new ArrayList<>();
    }

    public String getHelp() {
        return String.format("%s%s:%s invoke npc from a template",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "invoke";
    }
}
