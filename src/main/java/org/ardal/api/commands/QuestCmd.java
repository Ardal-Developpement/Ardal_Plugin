package org.ardal.api.commands;

import org.ardal.Ardal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface QuestCmd {
     void execute(Ardal plugin, Player player, Command command, String s, List<String> argv);

     List<String> getTabComplete(Ardal plugin, CommandSender player, Command command, String s, List<String> argv);

     String getHelp();

     String getCmdName();
}
