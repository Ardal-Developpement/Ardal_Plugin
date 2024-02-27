package org.ardal.commands.playerinfo.remove;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.PlayerInfoManager;
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

public class RemoveFinishedQuestCmd implements ArdalCmd {
    @Override
    public boolean execute(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.size() < 2){
            return false;
        }

        OfflinePlayer player = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
        if(player == null) {
            sender.sendMessage("Invalid player name.");
            return true;
        }

        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);

        String questName = StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()));
        if(!questManager.questExist(questName)){
            sender.sendMessage("Invalid quest name.");
            return true;
        }

        if(!playerInfoManager.getPlayerFinishedQuests(player).contains(questName)){
            sender.sendMessage("Player don't have this active quest.");
            return true;
        }

        playerInfoManager.removePlayerFinishedQuest(player, questName);
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, Command command, String s, List<String> argv) {
        if(argv.isEmpty()){
            return new ArrayList<>();
        }

        if(argv.size() == 1){
            return TabCompleteUtils.getTabCompleteFromStrList(BukkitUtils.getOfflinePlayerNamesAsList(), argv);
        }

        OfflinePlayer player = BukkitUtils.getOfflinePlayerFromName(argv.get(0));
        if(player == null) { return new ArrayList<>(); }

        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);

        return TabCompleteUtils.getTabCompleteFromStrList(
                playerInfoManager.getPlayerFinishedQuests(player),
                StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()))
        );
    }

    @Override
    public String getHelp() {
        return String.format("%s%s:%s remove an finished quest to a player.",
                ChatColor.GOLD,
                getCmdName(),
                ChatColor.WHITE);
    }

    @Override
    public String getCmdName() {
        return "finishedQuest";
    }
}
