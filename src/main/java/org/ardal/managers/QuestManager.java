package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.quests.QuestManagerInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.quests.*;
import org.ardal.commands.quests.edit.EditQuestManager;
import org.ardal.commands.quests.get.GetQuestManager;
import org.ardal.commands.quests.give.GiveQuestManager;
import org.ardal.commands.quests.set.SetQuestManager;
import org.ardal.db.Database;
import org.ardal.models.MQuest;
import org.ardal.objects.QuestObj2;
import org.ardal.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuestManager extends ArdalCmdManager implements QuestManagerInfo, ArdalManager {

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
    public boolean createNewQuest(ItemStack book, List<ItemStack> itemsRequest, List<ItemStack> itemsReward){
        Database db = Ardal.getInstance().getDb();
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);

        int itemsRequestId = db.gettItemGroup().saveItemsByGroupId(itemsRequest);
        int itemsRewardId = db.gettItemGroup().saveItemsByGroupId(itemsReward);
        String bookId = customItemManager.addItem(book);
        String questName = book.getItemMeta().getDisplayName();

        MQuest mQuest = new MQuest(questName, bookId, "", itemsRequestId, itemsRewardId, true, false);

        return db.gettQuest().saveQuest(mQuest) != -1;
    }

    @Override
    public List<String> getAllQuestNames(boolean includeDelete) {
        return Ardal.getInstance().getDb().gettQuest().getAllQuestNames(includeDelete);
    }

    @Override
    public boolean getIsQuestExist(String questName, boolean includeDeleted) {
        return this.getQuestObj(questName, includeDeleted) != null;
    }

    @Override
    public @Nullable QuestObj2 getQuestObj(String questName, boolean includeDeleted) {
        return new QuestObj2(Ardal.getInstance().getDb().gettQuest().getQuestByName(questName, includeDeleted));
    }

    @Override
    public @Nullable QuestObj2 getQuestObj(String questName) {
        return this.getQuestObj(questName, false);
    }
}
