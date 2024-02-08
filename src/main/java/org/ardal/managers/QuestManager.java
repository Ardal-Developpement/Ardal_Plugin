package org.ardal.managers;

import com.google.gson.stream.MalformedJsonException;
import org.ardal.Ardal;
import org.ardal.api.commands.ArdalCmdManager;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.quests.QuestInfo;
import org.ardal.commands.BaseCmdAlias;
import org.ardal.commands.quests.*;
import org.ardal.db.quest.QuestDB;
import org.ardal.db.quest.QuestObj;
import org.ardal.utils.JsonUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class QuestManager extends ArdalCmdManager implements QuestInfo, ArdalManager {
    private final QuestDB questDB;

    public QuestManager(){
        super(BaseCmdAlias.BASE_QUEST_CMD_ALIAS);

        this.registerCmd(new AddQuest());
        this.registerCmd(new GetQuestBook());
        this.registerCmd(new ListQuest());
        this.registerCmd(new OpenQuestGUI());
        this.registerCmd(new RemoveQuest());
        this.registerCmd(new SetStateQuest());

        this.questDB = new QuestDB(Ardal.getInstance().getDataFolder().toPath().toAbsolutePath());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        this.questDB.saveDB();
    }

    public QuestDB getQuestDB() {
        return questDB;
    }

    public QuestObj getQuestObj(String questName) {
        try {
            return this.getQuestDB().getQuestAsQuestObj(questName);
        } catch (MalformedJsonException e) {
            return null;
        }
    }

    public QuestObj getQuestObj(CommandSender sender, String questName){
        try {
            return this.getQuestDB().getQuestAsQuestObj(questName);
        } catch (MalformedJsonException e) {
            sender.sendMessage("Invalid quest.");
            return null;
        }
    }

    @Override
    public boolean addQuest(CommandSender sender, ItemStack book, List<ItemStack> itemsRequest, List<ItemStack> itemsReward) {
        if (!(book.getType() == Material.WRITABLE_BOOK || book.getType() == Material.WRITTEN_BOOK)) {
            sender.sendMessage("Try to add item that not a writable book or a written book.");
            return false;
        }

        if (!(book.hasItemMeta() && book.getItemMeta() instanceof BookMeta)) {
            sender.sendMessage("Book do not have book meta.");
            return false;
        }

        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        String questName = bookMeta.getDisplayName().trim();

        if(questName.isEmpty()) {
            sender.sendMessage("Invalid quest name.");
            return false;
        }

        if(this.questDB.getQuestAsJsonObject(questName) != null){
            Ardal.writeToLogger("Overwriting the quest: " + questName + " .");
        }

        questDB.getDb().add(questName, new QuestObj(book, itemsRequest, itemsReward, false).toJson());
        questDB.saveDB();

        sender.sendMessage("Success to add quest book");
        return true;
    }

    @Override
    public boolean removeQuest(CommandSender sender, String questName) {
        if(this.getQuestDB().getQuestAsJsonObject(questName) == null){
            this.getQuestDB().getDb().remove(questName);
            sender.sendMessage("Success to remove quest.");
            return true;
        }

        sender.sendMessage("Unknown quest.");
        return false;
    }

    @Override
    public ItemStack getQuestBook(CommandSender sender, String questName) {
        return this.getQuestObj(sender, questName).getBook();
    }

    @Override
    public List<ItemStack> getItemsQuestRequest(CommandSender sender, String questName) {
        return this.getQuestObj(sender, questName).getItemsRequest();
    }

    @Override
    public List<ItemStack> getItemQuestReward(CommandSender sender, String questName) {
        return this.getQuestObj(sender, questName).getItemsReward();
    }

    @Override
    public List<QuestObj> getAllQuestObj() {
        List<QuestObj> questObjs = new ArrayList<>();
        for(String questName : JsonUtils.getKeySet(this.getQuestDB().getDb())){
            questObjs.add(this.getQuestObj(questName));
        }

        return questObjs;
    }
}
