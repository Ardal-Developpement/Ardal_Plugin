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
import org.ardal.db.tables.TQuestPlayer;
import org.ardal.db.tables.TQuest;
import org.ardal.models.MQuest;
import org.ardal.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuestManager extends ArdalCmdManager implements QuestInfo, ArdalManager {
    private final TQuest tQuests;
    private final TQuestPlayer tQuestPlayer;
    private final TPlayer tPlayers;
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

        this.tQuests = db.gettQuest();
        this.tQuestPlayer = db.gettQuestPlayer();
        this.tPlayers = db.gettPlayer();
    }

    @Nullable
    private MQuest getQuestByName(String questName){
        return Ardal.getInstance().getDb().gettQuest().getQuestByName(questName);
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
    public boolean addQuest(ItemStack book, List<ItemStack> itemsRequest, List<ItemStack> itemsReward) {
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
    public boolean removeQuest(String questName) {
        return Ardal.getInstance().getDb().gettQuest().deleteQuestByName(questName);
    }

    @Override
    public ItemStack getQuestBook(String questName) {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        return customItemManager.getItem(this.getQuestByName(questName).getBookId());
    }

    @Override
    public List<ItemStack> getItemsQuestRequest(String questName) {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        List<String>  itemIds = Ardal.getInstance().getDb().gettItemGroup().getItemsByGroupId(
                this.getQuestByName(questName).getRequestItemId());

        return customItemManager.getItems(itemIds);
    }

    @Override
    public List<ItemStack> getItemQuestReward(String questName) {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        List<String>  itemIds = Ardal.getInstance().getDb().gettItemGroup().getItemsByGroupId(
                this.getQuestByName(questName).getRewardItemId());

        return customItemManager.getItems(itemIds);
    }

    @Override
    public List<String> getAllQuestNames() {
        return Ardal.getInstance().getDb().gettQuest().getAllQuestNames();
    }

    @Override
    public @Nullable Boolean setQuestActivity(String questName, boolean state) {
        MQuest mQuest = this.getQuestByName(questName);
        mQuest.setIsActive(state);
        return mQuest.saveQuest();
    }

    @Override
    public @Nullable Boolean setQuestDeleted(String questName) {
        MQuest mQuest = this.getQuestByName(questName);
        mQuest.setIsDelete(true);
        return mQuest.saveQuest();
    }

    @Override
    public boolean setQuestSynopsis(String questName, @Nullable String synopsis) {
        MQuest mQuest = this.getQuestByName(questName);
        mQuest.setSynopsis(synopsis);
        return mQuest.saveQuest();
    }

    @Override
    public @Nullable Boolean getQuestActivity(String questName) {
        return this.getQuestByName(questName).getIsActive();
    }

    @Override
    public boolean getQuestDeleteState(String questName) {
        return this.getQuestByName(questName).getIsDelete();
    }

    @Override
    public boolean questExist(String questName) {
        return this.getQuestByName(questName) != null;
    }

    @Override
    public @Nullable String getQuestSynopsis(String questName) {
        return this.getQuestByName(questName).getSynopsis();
    }
}
