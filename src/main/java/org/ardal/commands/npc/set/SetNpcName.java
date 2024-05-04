package org.ardal.commands.npc.set;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.exceptions.NpcNotFound;
import org.ardal.objects.NpcObj;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SetNpcName implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2) {
            return false;
        }

        String uuid = argv.get(0);
        String name = argv.get(1);

        try {
            NpcObj npc = NpcObj.getNpc(uuid);
            npc.setName(name);

        } catch (NpcNotFound e) {
            sender.sendMessage("Npc not found.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNames(), argv);
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
        return "name";
    }
}