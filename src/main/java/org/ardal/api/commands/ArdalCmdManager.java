package org.ardal.api.commands;

import org.ardal.Ardal;
import org.ardal.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArdalCmdManager implements CommandExecutor, TabCompleter {
    private final List<ArdalCmd> registeredCmd;

    private final String baseCmdAlias;

    public ArdalCmdManager(String baseCmdAlias){
        this.registeredCmd = new ArrayList<>();
        this.baseCmdAlias = baseCmdAlias;

        Ardal.getInstance().getCommand(this.baseCmdAlias).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command can be only used by player.");
            return true;
        }

        List<String> argv = StringUtils.getStrListFromStrArray(strings);

        Player player = (Player) sender;

        if(argv.isEmpty()){
            printCmdHelp(player);
            return false;
        }

        ArdalCmd cmd = findCmdByName(argv.get(0));

        if(cmd == null) {
            printCmdHelp(player);
            return false;
        }

        try {
            if(argv.size() > 1){
                argv.remove(0);
            }
            cmd.execute(player, command, s, argv);
        } catch (Exception e){
            Ardal.getInstance().getLogger().severe(e.toString());
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> argv = StringUtils.getStrListFromStrArray(strings);
        List<String> tabComplete = new ArrayList<>();

        try {
            for (ArdalCmd cmd : getRegisteredCmd()) {
                String cmdName = cmd.getCmdName();

                if (cmdName.toLowerCase().startsWith(argv.get(0).toLowerCase())) {
                    if (argv.size() == 1) {
                        tabComplete.add(cmdName);
                    } else {
                        return cmd.getTabComplete(sender, command, s, argv.subList(1, argv.size()));
                    }
                }
            }
        } catch (Exception e) {
            Ardal.getInstance().getLogger().severe(e.toString());
            return null;
        }

        return tabComplete;
    }

    private void printCmdHelp(Player player){
        try {
            for(ArdalCmd cmd : getRegisteredCmd()){
                player.sendMessage(cmd.getHelp());
            }
        } catch (Exception e) {
            Ardal.getInstance().getLogger().severe(e.toString());
        }
    }

    private ArdalCmd findCmdByName(String cmdName){
        for(ArdalCmd cmd : this.getRegisteredCmd()){
            if(cmd.getCmdName().equals(cmdName)){
                return cmd;
            }
        }

        return null;
    }

    public void registerCmd(ArdalCmd cmd){
        this.registeredCmd.add(cmd);
    }

    public List<ArdalCmd> getRegisteredCmd() {
        return registeredCmd;
    }

    public String getBaseCmdAlias() {
        return baseCmdAlias;
    }
}
