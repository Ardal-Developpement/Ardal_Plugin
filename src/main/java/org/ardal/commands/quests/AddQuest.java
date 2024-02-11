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
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!(item.getType() == Material.WRITABLE_BOOK || item.getType() == Material.WRITTEN_BOOK)){
            player.sendMessage("Please take a written book in your main hand.");
            return;
        }

        new QuestObj(item, new ArrayList<>(), new ArrayList<>(), false).save();
        player.sendMessage("Success to add quest.");
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

