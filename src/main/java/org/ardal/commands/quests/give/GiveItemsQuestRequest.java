package org.ardal.commands.quests.give;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.objects.QuestObj;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.PlayerUtils;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GiveItemsQuestRequest implements ArdalCmd {
    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            sender.sendMessage(getHelp());
            return;
        }

        String questName = StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()));
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);

        OfflinePlayer offlinePlayer = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
        if(offlinePlayer == null
                || offlinePlayer.getName() == null
                || offlinePlayer.getPlayer() == null)
        {
            sender.sendMessage("Invalid player name.");
            return;
        }

        QuestObj questObj = questManager.getQuestObj(questName);
        if(questObj == null){
            sender.sendMessage("Invalid quest name.");
            return;
        }

        for(ItemStack item : questObj.getItemsRequest()) {
            PlayerUtils.giveItemStackToPlayer(item, offlinePlayer.getPlayer());
        }

        sender.sendMessage("Success to give items request for: " + questName + " to: " + offlinePlayer.getName());
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        if(argv.size() < 2){
            return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv.get(0));
        }

        return TabCompleteUtils.getTabCompleteFromStrList(Ardal.getInstance().getManager(QuestManager.class).getQuestDB().getKeySet(), argv.subList(1, argv.size()));
    }

    @Override
    public String getHelp() {
        return getCmdName() + "[player] [quest name] :  give to the player the items quest requested";
    }

    @Override
    public String getCmdName() {
        return "itemsRequest";
    }
}
