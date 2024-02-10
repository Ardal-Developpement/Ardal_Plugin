package org.ardal.commands.quests.edit;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.callbacks.quest.EditQuestItemsRequestCallBack;
import org.ardal.inventories.CISize;
import org.ardal.inventories.implementations.CIDropBox;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EditItemsQuestRequest implements ArdalCmd {


    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            sender.sendMessage("Invalid command format.");
            return;
        }

        Player player = (Player) sender;
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);

        QuestObj questObj = questManager.getQuestObj(StringUtils.getStringFromConcatStringList(argv));
        if(questObj == null){
            player.sendMessage("Invalid quest name.");
            return;
        }

        String title = questObj.getQuestName() + " items request:";
        CIDropBox ciDropBox = new CIDropBox(title, CISize.CIS_9x6, player, new EditQuestItemsRequestCallBack(questObj));

        for(ItemStack item : questObj.getItemsRequest()){
            ciDropBox.addItem(item);
        }

        ciDropBox.showInventory();
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        return TabCompleteUtils.getTabCompleteFromStrList(questManager.getAllQuestNames(), argv.get(0));
    }

    @Override
    public String getHelp() {
        return getCmdName() + " : edit the items request by the quest.";
    }

    @Override
    public String getCmdName() {
        return "itemsRequest";
    }


}
