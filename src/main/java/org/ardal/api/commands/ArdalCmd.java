package org.ardal.api.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface ArdalCmd {
     void execute(CommandSender sender, Command command, String s, List<String> argv);

     List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv);

     /**
      * Get the help of the command.
      *
      * @return command help
      */
     String getHelp();

     String getCmdName();
}
