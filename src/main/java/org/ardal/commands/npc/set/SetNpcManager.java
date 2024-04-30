package org.ardal.commands.npc.set;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.commands.ArdalCmdNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SetNpcManager extends ArdalCmdNode implements ArdalCmd {
    private static final String CMD_NAME = "set";

    public SetNpcManager(){
        super(CMD_NAME);

        this.registerCmd(new SetNpcSkin());
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        return this.onSubCmd(sender, command, s, argv);
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return this.onSubTabComplete(sender, command, s, argv);
    }

    @Override
    public String getHelp() {
        return this.getNodeHelp();
    }

    @Override
    public String getCmdName() {
        return CMD_NAME;
    }
}
