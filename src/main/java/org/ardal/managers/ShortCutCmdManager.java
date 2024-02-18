package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.commands.ArdalCmdNode;
import org.ardal.api.managers.ArdalManager;
import org.ardal.commands.ECCmd;
import org.ardal.commands.playerinfo.get.GetAdventureLevel;
import org.ardal.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShortCutCmdManager extends ArdalCmdNode implements CommandExecutor, TabCompleter, ArdalManager {
    public ShortCutCmdManager() {
        super("");

        this.registerCmd(new ECCmd());
        this.registerCmd(new GetAdventureLevel());

        for(ArdalCmd cmd : this.getRegisteredCmd()){
            Ardal.getInstance().getCommand(cmd.getCmdName()).setExecutor(this);
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        ArdalCmd cmd = findCmdByName(command.getName());

        if(cmd == null) {
            sender.sendMessage("Unknown command. Type \"/help\" for help.");
            return false;
        }

        try {
            if(!cmd.execute(sender, command, s, StringUtils.getStrListFromStrArray(strings))){
                if(!(cmd instanceof ArdalCmdNode)){
                    sender.sendMessage(getHelpSection(cmd.getCmdName(), false));
                    sender.sendMessage(cmd.getHelp());
                }

                return true;
            }
        } catch (Exception e){
            Ardal.getInstance().getLogger().severe(e.toString());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> tabComplete = new ArrayList<>();

        try {
            for (ArdalCmd cmd : getRegisteredCmd()) {
                String cmdName = cmd.getCmdName();

                if (cmdName.toLowerCase().startsWith(command.getName().toLowerCase())) {
                    return cmd.getTabComplete(sender, command, s,StringUtils.getStrListFromStrArray(strings));
                }
            }
        } catch (Exception e) {
            Ardal.getInstance().getLogger().severe(e.toString());
            return null;
        }

        return tabComplete;
    }
}
