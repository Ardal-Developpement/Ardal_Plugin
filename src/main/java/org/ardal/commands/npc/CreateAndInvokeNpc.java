package org.ardal.commands.npc;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.npc.NpcType;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
/*
public class CreateAndInvokeNpc implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            return false;
        }

        Player player = (Player) sender;

        NpcType type = NpcType.getNpcTypeByName(argv.get(0));
        if(type == null) {
            sender.sendMessage("Invalid npc type");
            return true;
        }

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);

        String npcName = StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()));
        if(npcName.trim().isEmpty()){
            sender.sendMessage("Invalid npc name.");
            return true;
        }

        customNPCManager.createNewNpc(npcName, type, player.getLocation());
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty() || argv.size() > 2){
            return new ArrayList<>();
        }

        List<String> npcTypes = new ArrayList<>();
        for(NpcType type : NpcType.values()){
            npcTypes.add(type.toString());
        }

        return TabCompleteUtils.getTabCompleteFromStrList(npcTypes, argv.get(0));
    }

    public String getHelp() {
        return String.format("%s%s:%s create a new npc.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "create";
    }
}
*/