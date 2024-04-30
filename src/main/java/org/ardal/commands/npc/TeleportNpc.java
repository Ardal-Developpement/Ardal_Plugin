package org.ardal.commands.npc;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.exceptions.NpcNotFound;
import org.ardal.managers.NPCManager;
import org.ardal.objects.NpcObj;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportNpc implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()) {
            return false;
        }

        String uuid = argv.get(0);

        try {
            NpcObj npc = NpcObj.getNpc(uuid);
            Player player = (Player) sender;

            if(npc.setLocation(player)) {
                sender.sendMessage("Successfully teleported: " + npc.getName() + " to " + player.getDisplayName());
            } else {
                sender.sendMessage("Failed to teleport: " + npc.getName() + " to " + player.getDisplayName());
            }
        } catch (NpcNotFound e) {
            sender.sendMessage("Npc not found.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    public String getHelp() {
        return String.format("%s%s:%s teleport the npc.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "teleport";
    }
}