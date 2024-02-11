package org.ardal.commands.quests.give;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.commands.ArdalCmdNode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GiveQuestManager extends ArdalCmdNode implements ArdalCmd {
    private static final String CMD_NAME = "give";


    public GiveQuestManager(){
        super(CMD_NAME);
        this.registerCmd(new GiveQuestBook());
        this.registerCmd(new GiveItemsQuestRequest());
        this.registerCmd(new GiveItemsQuestReward());
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
        return String.format("%s%s:%s give items of quest.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return CMD_NAME;
    }
}
