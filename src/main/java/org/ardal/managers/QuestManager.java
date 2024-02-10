package org.ardal.managers;

import com.google.gson.stream.MalformedJsonException;
import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.quests.QuestInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.quests.*;
import org.ardal.commands.quests.edit.EditQuestManager;
import org.ardal.commands.quests.give.GiveQuestManager;
import org.ardal.commands.quests.set.SetQuestManager;
import org.ardal.db.QuestDB;
import org.ardal.objects.QuestObj;
import org.ardal.utils.JsonUtils;
import org.ardal.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestManager extends ArdalCmdManager implements QuestInfo, ArdalManager {
    private final QuestDB questDB;

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

        this.questDB = new QuestDB(Ardal.getInstance().getDataFolder().toPath().toAbsolutePath());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        this.questDB.saveDB();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command can be only used by player.");
            return true;
        }

        return this.onSubCmd(commandSender, command, s, StringUtils.getStrListFromStrArray(strings));
    }

    public QuestDB getQuestDB() {
        return questDB;
    }

    public QuestObj getQuestObj(String questName){
        try {
            return this.getQuestDB().getQuestAsQuestObj(questName);
        } catch (MalformedJsonException e) {
            return null;
        }
    }

    @Override
    public boolean addQuest(ItemStack book, List<ItemStack> itemsRequest, List<ItemStack> itemsReward) {
        if (!(book.getType() == Material.WRITABLE_BOOK || book.getType() == Material.WRITTEN_BOOK)) {
            return false;
        }

        if (!(book.hasItemMeta() && book.getItemMeta() instanceof BookMeta)) {
            return false;
        }

        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        String questName = bookMeta.getDisplayName().trim();

        if(questName.isEmpty()) {
            return false;
        }

        if(this.questDB.getQuestAsJsonObject(questName) != null){
            Ardal.writeToLogger("Overwriting the quest: " + questName + " .");
        }

        new QuestObj(book, itemsRequest, itemsReward, false).save();
        return true;
    }

    @Override
    public boolean removeQuest(String questName) {
        if(this.getQuestDB().getQuestAsJsonObject(questName) != null){
            this.getQuestDB().getDb().remove(questName);
            this.getQuestDB().saveDB();
            return true;
        }

        return false;
    }

    @Override
    public ItemStack getQuestBook(String questName) {
        return this.getQuestObj(questName).getBook();
    }

    @Override
    public List<ItemStack> getItemsQuestRequest(String questName) {
        return this.getQuestObj(questName).getItemsRequest();
    }

    @Override
    public List<ItemStack> getItemQuestReward(String questName) {
        return this.getQuestObj(questName).getItemsReward();
    }

    @Override
    public List<String> getItemsQuestRequestId(String questName) {
        return this.getQuestObj(questName).getItemsRequestId();
    }

    @Override
    public List<String> getItemQuestRewardId(String questName) {
        return this.getQuestObj(questName).getItemsRewardId();
    }

    @Override
    public List<QuestObj> getAllQuestObj() {
        List<QuestObj> questObjs = new ArrayList<>();
        for(String questName : JsonUtils.getKeySet(this.getQuestDB().getDb())){
            questObjs.add(this.getQuestObj(questName));
        }

        Collections.sort(questObjs);
        return questObjs;
    }

    @Override
    public List<String> getAllQuestNames() {
        List<QuestObj> questObjs = this.getAllQuestObj();
        List<String> questNames = new ArrayList<>();

        for(QuestObj questObj : questObjs){
            questNames.add(questObj.getQuestName());
        }

        return questNames;
    }
}
