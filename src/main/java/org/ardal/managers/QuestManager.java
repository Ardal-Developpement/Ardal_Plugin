package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.quests.QuestInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.quests.*;
import org.ardal.commands.quests.edit.EditQuestManager;
import org.ardal.commands.quests.get.GetQuestManager;
import org.ardal.commands.quests.give.GiveQuestManager;
import org.ardal.commands.quests.set.SetQuestManager;
import org.ardal.db.Database;
import org.ardal.db.tables.TPlayer;
import org.ardal.db.tables.TQuest;
import org.ardal.db.tables.TQuestPlayer;
import org.ardal.objects.QuestObj;
import org.ardal.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuestManager extends ArdalCmdManager implements QuestInfo, ArdalManager {
    private final TQuest tQuest;
    private final TQuestPlayer tQuestPlayer;
    private final TPlayer tPlayer;
    public QuestManager() {
        super(BaseCmdAlias.BASE_QUEST_CMD_ALIAS);

        this.registerCmd(new AddQuest());
        this.registerCmd(new ListQuest());
        this.registerCmd(new OpenQuestGUI());
        this.registerCmd(new RemoveQuest());
        this.registerCmd(new HelpQuest());

        this.registerCmd(new SetQuestManager());
        this.registerCmd(new EditQuestManager());
        this.registerCmd(new GiveQuestManager());
        this.registerCmd(new GetQuestManager());

        Database db = Ardal.getInstance().getDb();

        this.tQuest = db.gettQuest();
        this.tQuestPlayer = db.gettQuestPlayer();
        this.tPlayer = db.gettPlayer();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command can be only used by player.");
            return true;
        }

        return this.onSubCmd(commandSender, command, s, StringUtils.getStrListFromStrArray(strings));
    }


    @Override
    public boolean addQuest(ItemStack book, List<ItemStack> itemsRequestId, List<ItemStack> itemsRewardId) {
        //MQuest mQuest = new MQuest()
        return true;
    }

    @Override
    public boolean removeQuest(String questName) {
        return false;
    }

    @Override
    public ItemStack getQuestBook(String questName) {
        return null;
    }

    @Override
    public List<ItemStack> getItemsQuestRequest(String questName) {
        return null;
    }

    @Override
    public List<ItemStack> getItemQuestReward(String questName) {
        return null;
    }

    @Override
    public List<String> getItemsQuestRequestId(String questName) {
        return null;
    }

    @Override
    public List<String> getItemQuestRewardId(String questName) {
        return null;
    }

    @Override
    public List<QuestObj> getAllQuestObj() {
        return null;
    }

    @Override
    public List<String> getAllQuestNames() {
        return null;
    }

    @Override
    public @Nullable Boolean setQuestActivity(String questName, boolean state) {
        return null;
    }

    @Override
    public @Nullable Boolean setQuestDeleted(String questName) {
        return null;
    }

    @Override
    public void setQuestSynopsis(String questName, @Nullable String synopsis) {

    }

    @Override
    public @Nullable Boolean getQuestActivity(String questName) {
        return null;
    }

    @Override
    public boolean getQuestDeleteState(String questName) {
        return false;
    }

    @Override
    public boolean questExist(String questName) {
        return false;
    }

    @Override
    public @Nullable String getQuestSynopsis(String questName) {
        return null;
    }
}
