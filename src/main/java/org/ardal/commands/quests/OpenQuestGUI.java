package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.QuestCmd;
import org.ardal.utils.QuestUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OpenQuestGUI implements QuestCmd {
    @Override
    public void execute(Ardal plugin, Player player, Command command, String s, List<String> argv) {
        List<ItemStack> questBooks = QuestUtils.getAllQuestBook(plugin.getQuestManager().getQuestDB(), player);

        int inventorySize = questBooks.size() / 9;
        if(questBooks.size() % 9 > 0){
            inventorySize++;
        }
        inventorySize *= 9;

        Inventory inventory = Bukkit.createInventory(player, inventorySize, ChatColor.GOLD + "Quest:");
        questBooks.forEach(inventory::addItem);

        player.openInventory(inventory);
    }

    @Override
    public List<String> getTabComplete(Ardal plugin, CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return getCmdName() + " -> open the quest gui.";
    }

    @Override
    public String getCmdName() {
        return "open";
    }
}
