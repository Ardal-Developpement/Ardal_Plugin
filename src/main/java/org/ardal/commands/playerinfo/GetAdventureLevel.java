package org.ardal.commands.playerinfo;


import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.PlayerInfoManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetAdventureLevel implements ArdalCmd {
    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        long level =  Ardal.getInstance().getManager(PlayerInfoManager.class).getAdventureLevel(player);
        player.sendMessage("Your current adventure level is: " + level);
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return getCmdName() + " -> get your adventure level.";
    }

    @Override
    public String getCmdName() {
        return "adventureLevel";
    }
}
