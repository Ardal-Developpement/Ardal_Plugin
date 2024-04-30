package org.ardal.commands.npc.set;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.exceptions.NpcNotFound;
import org.ardal.managers.NPCManager;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.objects.NpcObj;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetNpcSkin implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2) {
            return false;
        }

        String uuid = argv.get(0);
        String skinName = argv.get(1);

        try {
            NpcObj npc = new NpcObj(uuid);
            npc.setNpcSkin(skinName, true);

        } catch (NpcNotFound e) {
            sender.sendMessage("Npc not found.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv);
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s set npc skin.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "skin";
    }
}