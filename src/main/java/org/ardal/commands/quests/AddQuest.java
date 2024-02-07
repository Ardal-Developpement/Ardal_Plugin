package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.utils.QuestUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AddQuest implements ArdalCmd {

    @Override
    public void execute(Ardal plugin, Player player, Command command, String s, List<String> argv) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!(item.getType() == Material.WRITABLE_BOOK || item.getType() == Material.WRITTEN_BOOK)){
            player.sendMessage("Please take a written book in your main hand.");
        }

        BookMeta bookMeta = (BookMeta) Objects.requireNonNull(item.getItemMeta());
        QuestUtils.addQuestBook(plugin.getQuestManager().getQuestDB(), player, bookMeta);
    }

    @Override
    public List<String> getTabComplete(Ardal plugin, CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return getCmdName() + " (with writable book in main hand) -> add a new quest book.";
    }

    @Override
    public String getCmdName() {
        return "add";
    }
}

