package org.ardal.commands.quests.invoke;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.managers.CustomNPCManager;
import org.ardal.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateAndInvokeQuestNpc implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return false;
        }

        Player player = (Player) sender;

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        String npcName = StringUtils.getStringFromConcatStringList(argv);
        if(npcName.trim().isEmpty()){
            sender.sendMessage("Invalid npc name.");
            return true;
        }

        customNPCManager.createNewNpc(npcName, CustomNpcType.QUEST_NPC, player.getLocation());
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
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
