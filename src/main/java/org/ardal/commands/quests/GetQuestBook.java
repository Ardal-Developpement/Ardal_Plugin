package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.utils.PlayerInventoryUtils;
import org.ardal.utils.QuestUtils;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GetQuestBook implements ArdalCmd {
    @Override
    public void execute(Ardal plugin, Player player, Command command, String s, List<String> argv) {
        String questName = StringUtils.getStringFromConcatStringList(argv);

        ItemStack book = QuestUtils.getQuestBook(plugin.getQuestManager().getQuestDB(), player, questName);
        if(book == null) { return; }

        PlayerInventoryUtils.giveItemStackToPlayer(book, player);
        player.sendMessage("Success to give quest book.");
    }

    @Override
    public List<String> getTabComplete(Ardal plugin, CommandSender player, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteForQuestName(plugin.getQuestManager().getQuestDB(), argv);
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [quest name] -> give the associate quest book.";
    }

    @Override
    public String getCmdName() {
        return "getQuestBook";
    }
}
