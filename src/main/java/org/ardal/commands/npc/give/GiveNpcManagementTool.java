package org.ardal.commands.npc.give;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;/*

public class GiveNpcManagementTool implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player;
        if(argv.isEmpty()){
            player = (Player) sender;
        } else{
            OfflinePlayer offlinePlayer = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
            if(offlinePlayer == null){
                sender.sendMessage("Player not found.");
                return true;
            }

            player = offlinePlayer.getPlayer();
        }

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        if(!customNPCManager.giveManagementToolToPlayer(player)){
            player.sendMessage("Failed to give npc management tool.");
        }

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv);
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s give npc management tool.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "managementTool";
    }
}*/
