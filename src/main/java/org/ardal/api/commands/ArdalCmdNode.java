package org.ardal.api.commands;

import org.ardal.Ardal;
import org.ardal.utils.ListUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class ArdalCmdNode {
    private final List<ArdalCmd> registeredCmd;

    public ArdalCmdNode(){
        this.registeredCmd = new ArrayList<>();
    }

    public boolean onSubCmd(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            sender.sendMessage(this.getNodeHelp());
            return false;
        }

        ArdalCmd cmd = findCmdByName(argv.get(0));

        if(cmd == null) {
            sender.sendMessage(this.getNodeHelp());
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
                    } else if(cmdName.equalsIgnoreCase(argv.get(0))) {
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

    /**
     * Get the help of the sub commands of the nodes
     *
     * @return help of the node
     */
    public String getNodeHelp(){
        List<ArdalCmd> cmds = this.registeredCmd;
        cmds.sort(Comparator.comparing(ArdalCmd::getCmdName));

        if(cmds.isEmpty()) { return ""; }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < cmds.size() - 1; i++){
            stringBuilder.append(cmds.get(i).getHelp()).append("\n");
        }

        stringBuilder.append(cmds.get(cmds.size() - 1).getHelp());
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
