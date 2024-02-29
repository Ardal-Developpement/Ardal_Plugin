package org.ardal.commands.quests;

import org.ardal.api.commands.ArdalCmd;
import org.ardal.objects.QuestObj;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class AddQuest implements ArdalCmd {

    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand().clone();

        if(!(item.getType() == Material.WRITABLE_BOOK || item.getType() == Material.WRITTEN_BOOK)){
            player.sendMessage("Please take a written book in your main hand.");
            return true;
        }

        new QuestObj(item, new ArrayList<>(), new ArrayList<>(), true).save();
        player.sendMessage("Success to add quest.");
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    public String getHelp() {
        return String.format("%s%s:%s add a new quest book.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "add";
    }
}

