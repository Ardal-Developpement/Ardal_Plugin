package org.ardal.commands.quests.set;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmd;
import org.ardal.managers.QuestManager;
import org.ardal.utils.StringUtils;
import org.ardal.utils.TabCompleteUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetIsActiveQuest implements ArdalCmd {
    @Override
    public void execute(CommandSender sender, Command command, String s, List<String> argv) {
        System.out.println("Test Is Active Quest");
        Player player = (Player) sender;
        String questName = StringUtils.getStringFromConcatStringList(argv.subList(1, argv.size()));
        JsonObject questObj = Ardal.getInstance().getManager(QuestManager.class).getQuestDB().getQuestAsJsonObject(questName);

        if(questObj == null){
            player.sendMessage("Unknown quest name.");
            return;
        }

        boolean state = argv.get(0).toLowerCase().trim().equals("true");
        questObj.addProperty("isActive", state);
        Ardal.getInstance().getManager(QuestManager.class).getQuestDB().saveDB();

        player.sendMessage("Set quest " + questName + " visibility to " + state);
    }

    @Override
    public List<String> getTabComplete(CommandSender player, Command command, String s, List<String> argv) {
        if(argv.size() == 1){
            return TabCompleteUtils.getTabCompleteForBool(argv.get(0));
        }

        return TabCompleteUtils.getTabCompleteFromStrList(Ardal.getInstance().getManager(QuestManager.class).getQuestDB().getKeySet(), argv.subList(1, argv.size()));
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [true|false] [quest name]";
    }

    @Override
    public String getCmdName() {
        return "isActive";
    }
}