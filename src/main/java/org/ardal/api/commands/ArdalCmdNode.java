package org.ardal.api.commands;

import org.ardal.Ardal;
import org.ardal.utils.ListUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class ArdalCmdNode {
    private final List<ArdalCmd> registeredCmd;

    public ArdalCmdNode(){
        this.registeredCmd = new ArrayList<>();
    }

    public boolean onSubCmd(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            printCmdHelp(sender);
            return false;
        }

        ArdalCmd cmd = findCmdByName(argv.get(0));

        if(cmd == null) {
            printCmdHelp(sender);
            return false;
        }

        try {
            ListUtils.removeFirstIfPossible(argv);

            cmd.execute(sender, command, s, argv);
        } catch (Exception e){
            Ardal.getInstance().getLogger().severe(e.toString());
            return true;
        }

        return false;
    }

    public List<String> onSubTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
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

    public void registerCmd(ArdalCmd cmd) {
        this.registeredCmd.add(cmd);
    }

    public List<ArdalCmd> getRegisteredCmd() {
        return registeredCmd;
    }

    public void printCmdHelp(CommandSender sender){
        for(String cmdHelp : this.getCmdsHelp()){
            sender.sendMessage(cmdHelp);
        }
    }

    public List<String> getCmdsHelp(){
        List<String> commandsHelp = new ArrayList<>();
        for(ArdalCmd cmd : getRegisteredCmd()){
            commandsHelp.add(cmd.getHelp());
        }

        return commandsHelp;
    }

    public String getFormattedHelp(){
        List<String> cmdsHelp = this.getCmdsHelp();
        StringBuilder stringBuilder = new StringBuilder(cmdsHelp.get(0));
        for(int i = 1; i < cmdsHelp.size(); i++){
            stringBuilder.append(cmdsHelp.get(i)).append("\n");
        }

        return stringBuilder.toString();
    }

    public ArdalCmd findCmdByName(String cmdName){
        for(ArdalCmd cmd : this.getRegisteredCmd()){
            if(cmd.getCmdName().equals(cmdName)){
                return cmd;
            }
        }

        return null;
    }
}
