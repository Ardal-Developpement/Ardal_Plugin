package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class OpenQuestGUI implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        List<String> questNames = questManager.getAllQuestNames(false);

        int inventorySize = questNames.size() / 9;
        if(questNames.size() % 9 > 0){
            inventorySize++;
        }
        inventorySize *= 9;

        Inventory inventory = Bukkit.createInventory(player, inventorySize, ChatColor.GOLD + "Quest:");
        for(String questName : questNames) {
            inventory.addItem(new QuestObj(questName).getQuestBook());
        }

        player.openInventory(inventory);
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    public String getHelp() {
        return String.format("%s%s:%s open the quest gui.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "open";
    }
}
