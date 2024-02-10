package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.objects.QuestObj;
import org.ardal.managers.QuestManager;
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
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        List<QuestObj> questObjs = Ardal.getInstance().getManager(QuestManager.class).getAllQuestObj();


        int inventorySize = questObjs.size() / 9;
        if(questObjs.size() % 9 > 0){
            inventorySize++;
        }
        inventorySize *= 9;

        Inventory inventory = Bukkit.createInventory(player, inventorySize, ChatColor.GOLD + "Quest:");
        for(QuestObj questObj : questObjs){
            inventory.addItem(questObj.getBook());
        }

        player.openInventory(inventory);
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
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
