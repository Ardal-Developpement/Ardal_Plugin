package org.ardal.commands.quests.give;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.PlayerUtils;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GiveQuestBook implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            return false;
        }
        Player player = (Player) sender;

        String questName = StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()));

        ItemStack book = Ardal.getInstance().getManager(QuestManager.class).getQuestBook(questName);
        if(book == null) {
            player.sendMessage("Invalid quest name.");
            return true;
        }

        OfflinePlayer offlinePlayer = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
        if(offlinePlayer == null
                || offlinePlayer.getName() == null
                || offlinePlayer.getPlayer() == null)
        {
            sender.sendMessage("Invalid player name.");
            return true;
        }

        PlayerUtils.giveItemStackToPlayer(book, offlinePlayer.getPlayer());
        player.sendMessage("Success to give quest book for: " + questName + " to: " + offlinePlayer.getName());
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        if(argv.size() < 2){
            return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv.get(0));
        }

        //return TabCompleteUtils.getTabCompleteFromStrList(Ardal.getInstance().getManager(QuestManager.class).getQuestDB().getKeySet(), argv.subList(1, argv.size()));
        return null;
    }

    public String getHelp() {
        return String.format("%s%s:%s give the associate quest book.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "questBook";
    }
}
