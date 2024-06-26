package org.ardal.commands.quests.edit;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.callbacks.quest.edititems.EditQuestItemsRequestCallBack;
import org.ardal.inventories.CIDropBox;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EditItemsQuestRequest implements ArdalCmd {

    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            return false;
        }

        Player player = (Player) sender;
        QuestObj questObj = new QuestObj(StringUtils.getStringFromConcatStringList(argv));

        if(!questObj.isQuestExist()){
            player.sendMessage("Invalid quest name.");
            return true;
        }

        String title = questObj.getQuestName() + " items request:";
        CIDropBox ciDropBox = new CIDropBox(title, 54, player, new EditQuestItemsRequestCallBack(questObj), null);

        for(ItemStack item : questObj.getItemsQuestRequest()){
            ciDropBox.addItem(item);
        }

        ciDropBox.showInventory();
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        return TabCompleteUtils.getTabCompleteFromStrList(questManager.getAllQuestNames(false), argv.get(0));
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s edit the items request by the quest.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "itemsRequest";
    }


}
