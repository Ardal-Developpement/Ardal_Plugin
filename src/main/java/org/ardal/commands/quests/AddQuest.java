package org.ardal.commands.quests;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
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
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!(item.getType() == Material.WRITABLE_BOOK || item.getType() == Material.WRITTEN_BOOK)){
            player.sendMessage("Please take a written book in your main hand.");
        }

        BookMeta bookMeta = (BookMeta) Objects.requireNonNull(item.getItemMeta());
        //QuestUtils.addQuestBook(plugin.getManager(QuestManager.class).getQuestDB(), player, bookMeta);
        if(Objects.requireNonNull(Ardal.getInstance().getManager(QuestManager.class)).addQuest(player, item, new ArrayList<>(), new ArrayList<>())){
            player.sendMessage("Success to add quest.");
        }
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
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

