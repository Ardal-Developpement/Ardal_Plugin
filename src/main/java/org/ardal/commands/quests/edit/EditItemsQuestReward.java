package org.ardal.commands.quests.edit;

import org.ardal.api.commands.ArdalCmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class EditItemsQuestReward implements ArdalCmd {
    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        sender.sendMessage("open edit reward item inventory");
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return getCmdName() + " : edit the items reward by the quest.";
    }

    @Override
    public String getCmdName() {
        return "itemsReward";
    }
}
