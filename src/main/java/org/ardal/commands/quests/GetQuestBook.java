package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.utils.PlayerUtils;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GetQuestBook implements ArdalCmd {
    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        String questName = StringUtils.getStringFromConcatStringList(argv);

        ItemStack book = Ardal.getInstance().getManager(QuestManager.class).getQuestBook(player, questName);
        if(book == null) { return; }

        PlayerUtils.giveItemStackToPlayer(book, player);
        player.sendMessage("Success to give quest book.");
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        return TabCompleteUtils.getTabCompleteFromStrList(Ardal.getInstance().getManager(QuestManager.class).getQuestDB().getKeySet(), argv);
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
