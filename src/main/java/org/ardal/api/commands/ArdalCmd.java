package org.ardal.api.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface ArdalCmd {
     void execute(Player player, Command command, String s, List<String> argv);

     List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv);

     String getHelp();

     String getCmdName();
}
