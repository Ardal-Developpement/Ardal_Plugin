package org.ardal.commands.quests.edit;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EditQuestBook implements ArdalCmd {
    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            sender.sendMessage("Invalid command format.");
            return;
        }

        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!(item.getType() == Material.WRITABLE_BOOK || item.getType() == Material.WRITTEN_BOOK)){
            player.sendMessage("Please take a written book in your main hand.");
            return;
        }

        String questName = item.getItemMeta().getDisplayName();
        QuestObj questObj = questManager.getQuestObj(questName);

        if(questObj == null){
            sender.sendMessage("Invalid quest name.");
            return;
        }

        questObj.setBook(item);
        questObj.save();
        player.sendMessage("Success to set new quest book.");
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        return new ArrayList<>();
    }

    @Override
    public String getHelp() {
        return getCmdName() + " (with writable book of the quest in main hand) : edit quest book for a quest." ;
    }

    @Override
    public String getCmdName() {
        return "questBook";
    }
}
