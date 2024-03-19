package org.ardal.commands.quests.give;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class GiveItemsQuestReward implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            return false;
        }

        String questName = StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()));
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);

        OfflinePlayer offlinePlayer = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
        if(offlinePlayer == null 
                || offlinePlayer.getName() == null 
                || offlinePlayer.getPlayer() == null)
        {
            sender.sendMessage("Invalid player name.");
            return true;
        }

        /*QuestObj questObj = questManager.getQuestObj(questName);
        if(questObj == null){
            sender.sendMessage("Invalid quest name.");
            return true;
        }

        for(ItemStack item : questObj.getItemsReward()) {
            PlayerUtils.giveItemStackToPlayer(item, offlinePlayer.getPlayer());
        }
        
        sender.sendMessage("Success to give items reward for: " + questName + " to: " + offlinePlayer.getName());*/
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        if(argv.size() < 2){
            return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv.get(0));
        }

        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        return TabCompleteUtils.getTabCompleteFromStrList(questManager.getAllQuestNames(), argv.subList(1, argv.size()));
    }

    public String getHelp() {
        return String.format("%s%s:%s give to the player the items quest rewarded.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "itemsReward";
    }
}
