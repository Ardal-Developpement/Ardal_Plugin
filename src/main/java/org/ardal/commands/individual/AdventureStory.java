package org.ardal.commands.individual;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.inventories.CIPickUp;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AdventureStory implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;

        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);

        List<String> finishedQuest = playerInfoManager.getPlayerFinishedQuests(player);
        List<ItemStack> questBooks = new ArrayList<>();
        finishedQuest.forEach(quest -> questBooks.add(questManager.getQuestBook(quest)));

        new CIPickUp("Your story", 36   , player, questBooks, true, -1).showInventory();

        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s get all of your story quest books.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "story";
    }
}
