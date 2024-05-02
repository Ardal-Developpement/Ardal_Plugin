package org.ardal.objects;

import org.ardal.Ardal;
import org.ardal.api.quests.QuestInfo;
import org.ardal.db.tables.TItemGroup;
import org.ardal.managers.ItemManager;
import org.ardal.models.MQuest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuestObj implements QuestInfo, Comparable<QuestObj> {
    private final MQuest mQuest;

    public QuestObj(MQuest mQuest) {
        this.mQuest = mQuest;
    }
    public QuestObj(String questName, boolean includeDeleted) {
        this.mQuest = Ardal.getInstance().getDb().gettQuest().getQuestByName(questName, includeDeleted);
    }

    public QuestObj(String questName) {
        this.mQuest = Ardal.getInstance().getDb().gettQuest().getQuestByName(questName, false);
    }

    public QuestObj(int questId, boolean includeDeleted) {
        this.mQuest = Ardal.getInstance().getDb().gettQuest().getQuestById(questId, includeDeleted);
    }

    public QuestObj(int questId) {
        this.mQuest = Ardal.getInstance().getDb().gettQuest().getQuestById(questId, false);
    }


    @Override
    public boolean isQuestExist() {
        return this.mQuest != null;
    }

    @Override
    public String getQuestName() {
        return this.mQuest.getName();
    }

    @Override
    public ItemStack getQuestBook() {
        ItemManager itemManager = Ardal.getInstance().getManager(ItemManager.class);
        ItemStack book = itemManager.getItem(this.mQuest.getBookId());
        String synopsis = this.getQuestSynopsis();

        if(synopsis != null) {
            ItemMeta meta = book.getItemMeta();

            List<String> lore = meta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }

            String[] lines = synopsis.split("\n");

            for (int i = lines.length - 1; i >= 0; i--) {
                lore.add(0, lines[i]);
            }

            meta.setLore(lore);
            book.setItemMeta(meta);
        }

        return book;
    }

    @Override
    public @Nullable String getQuestSynopsis() {
        return this.mQuest.getSynopsis();
    }

    @Override
    public List<ItemStack> getItemsQuestRequest() {
        ItemManager itemManager = Ardal.getInstance().getManager(ItemManager.class);
        List<String>  itemIds = Ardal.getInstance().getDb().gettItemGroup().getItemsByGroupId(
                this.mQuest.getRequestItemGroupId());

        return itemManager.getItems(itemIds);
    }

    @Override
    public List<ItemStack> getItemQuestReward() {
        ItemManager itemManager = Ardal.getInstance().getManager(ItemManager.class);
        List<String>  itemIds = Ardal.getInstance().getDb().gettItemGroup().getItemsByGroupId(
                this.mQuest.getRewardItemGroupId());

        return itemManager.getItems(itemIds);
    }

    @Override
    public boolean getQuestActivity() {
        return this.mQuest.getIsActive();
    }

    @Override
    public boolean setQuestActivity(boolean state) {
        this.mQuest.setIsActive(state);
        return this.mQuest.updateQuest();
    }

    @Override
    public boolean getQuestIsDeleted() {
        return this.mQuest.getIsDelete();
    }

    @Override
    public boolean setQuestBook(ItemStack questBook) {
        return false;
    }

    @Override
    public boolean setQuestSynopsis(@Nullable String synopsis) {
        this.mQuest.setSynopsis(synopsis);
        return this.mQuest.updateQuest();
    }

    @Override
    public boolean setItemsQuestRequest(List<ItemStack> items) {
        int oldGroupId = this.mQuest.getRequestItemGroupId();

        TItemGroup tItemGroup = Ardal.getInstance().getDb().gettItemGroup();
        int groupId = tItemGroup.saveItemsByGroupId(items);
        this.mQuest.setRequestItemGroupId(groupId);

        tItemGroup.deleteItemsByGroupId(oldGroupId);

        return this.mQuest.updateQuest();
    }

    @Override
    public boolean setItemsQuestReward(List<ItemStack> items) {
        int oldGroupId = this.mQuest.getRequestItemGroupId();

        TItemGroup tItemGroup = Ardal.getInstance().getDb().gettItemGroup();
        int groupId = tItemGroup.saveItemsByGroupId(items);
        this.mQuest.setRewardItemGroupId(groupId);

        tItemGroup.deleteItemsByGroupId(oldGroupId);

        return this.mQuest.updateQuest();
    }

    @Override
    public boolean setQuestDeleted(boolean state) {
        this.mQuest.setIsDelete(true);
        return this.mQuest.updateQuest();
    }

    @Override
    public int compareTo(QuestObj otherQuestObj) {
        return this.getQuestName().compareTo(otherQuestObj.getQuestName());
    }
}
