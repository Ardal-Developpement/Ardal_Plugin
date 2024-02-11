package org.ardal.api.commands;

import org.ardal.Ardal;
import org.ardal.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public abstract class ArdalCmdManager extends ArdalCmdNode implements CommandExecutor, TabCompleter {
    private final String baseCmdAlias;

    public ArdalCmdManager(String baseCmdAlias){
        super(baseCmdAlias);
        this.baseCmdAlias = baseCmdAlias;
        Ardal.getInstance().getCommand(this.baseCmdAlias).setExecutor(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return this.onSubTabComplete(commandSender, command, s, StringUtils.getStrListFromStrArray(strings));
    }


    public String getBaseCmdAlias() {
        return baseCmdAlias;
    }

}
