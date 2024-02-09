package org.ardal.commands.quests.edit;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.api.commands.ArdalCmdNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class EditQuestManager extends ArdalCmdNode implements ArdalCmd {

    public EditQuestManager(){
        this.registerCmd(new EditItemsQuestRequest());
        this.registerCmd(new EditItemsQuestReward());
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
        return "edit";
    }
}
