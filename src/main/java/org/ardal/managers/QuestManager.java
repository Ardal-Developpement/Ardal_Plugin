package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.QuestCmd;
import org.ardal.commands.playerinfo.GetAdventureLevel;
import org.ardal.commands.quests.*;
import org.ardal.db.QuestDB;
import org.ardal.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class QuestManager implements CommandExecutor, TabCompleter {
    private Ardal plugin;
    private final QuestDB questDB;
    private final List<QuestCmd> registeredQuestCmd;


    public QuestManager(Ardal plugin){
        this.plugin = plugin;
        this.questDB = new QuestDB(plugin.getDataFolder().toPath().toAbsolutePath());
        this.registeredQuestCmd = new ArrayList<>();

        this.addQuestCmd(new AddQuest());
        this.addQuestCmd(new GetQuestBook());
        this.addQuestCmd(new ListQuest());
        this.addQuestCmd(new OpenQuestGUI());
        this.addQuestCmd(new RemoveQuest());
        this.addQuestCmd(new SetStateQuest());
        this.addQuestCmd(new GetAdventureLevel());

        plugin.getCommand("quest").setExecutor(this);
    }

    private void addQuestCmd(QuestCmd cmd){
        this.registeredQuestCmd.add(cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command can be only used by player.");
            return true;
        }

        List<String> argv = StringUtils.getStrListFromStrArray(strings);

        Player player = (Player) sender;

        if(argv.isEmpty()){
            printCmdHelp(player);
            return false;
        }

        QuestCmd cmd = findQuestCmdByName(argv.get(0));

        if(cmd == null) {
            printCmdHelp(player);
            return false;
        }

        try {
            if(argv.size() > 1){
                argv.remove(0);
            }
            cmd.execute(this.plugin, player, command, s, argv);
        } catch (Exception e){
            e.printStackTrace();
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> argv = StringUtils.getStrListFromStrArray(strings);
        List<String> tabComplete = new ArrayList<>();

        try {
            for (QuestCmd cmd : getQuestCommands()) {
                String cmdName = cmd.getCmdName();

                if (cmdName.toLowerCase().startsWith(argv.get(0).toLowerCase())) {
                    if (argv.size() == 1) {
                        tabComplete.add(cmdName);
                    } else {
                        return cmd.getTabComplete(this.plugin, sender, command, s, argv.subList(1, argv.size()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return tabComplete;
    }

    private void printCmdHelp(Player player){
        try {
            for(QuestCmd cmd : getQuestCommands()){
                    player.sendMessage(cmd.getHelp());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private QuestCmd findQuestCmdByName(String cmdName){
        for(QuestCmd cmd : this.getQuestCommands()){
            if(cmd.getCmdName().equals(cmdName)){
                return cmd;
            }
        }

        return null;
    }

    public List<QuestCmd> getQuestCommands() {
        return this.registeredQuestCmd;
    }

    public QuestDB getQuestDB() {
        return questDB;
    }
}
