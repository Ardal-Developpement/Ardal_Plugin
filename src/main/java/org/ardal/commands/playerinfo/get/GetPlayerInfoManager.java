package org.ardal.commands.playerinfo.get;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.commands.ArdalCmdNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GetPlayerInfoManager extends ArdalCmdNode implements ArdalCmd {
    public GetPlayerInfoManager(){
        this.registerCmd(new GetAdventureLevel());
    }

    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        this.onSubCmd(sender, command, s, argv);
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return this.onSubTabComplete(sender, command, s, argv);
    }

    @Override
    public String getHelp() {
        return this.getFormattedHelp();
    }

    @Override
    public String getCmdName() {
        return "get";
    }
}
