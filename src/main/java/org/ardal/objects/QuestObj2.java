package org.ardal.objects;

import org.ardal.Ardal;
import org.ardal.api.quests.QuestInfo;
import org.ardal.managers.CustomItemManager;
import org.ardal.models.MQuest;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuestObj2 implements QuestInfo {
    private final MQuest mQuest;
    public QuestObj2(MQuest mQuest) {
        this.mQuest = mQuest;
    }

    @Override
    public ItemStack getQuestBook() {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        return customItemManager.getItem(this.mQuest.getBookId());
    }

    @Override
    public @Nullable String getQuestSynopsis() {
        return this.mQuest.getSynopsis();
    }

    @Override
    public List<ItemStack> getItemsQuestRequest() {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        List<String>  itemIds = Ardal.getInstance().getDb().gettItemGroup().getItemsByGroupId(
                this.mQuest.getRequestItemGroupId());

        return customItemManager.getItems(itemIds);
    }

    @Override
    public List<ItemStack> getItemQuestReward() {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        List<String>  itemIds = Ardal.getInstance().getDb().gettItemGroup().getItemsByGroupId(
                this.mQuest.getRewardItemGroupId());

        return customItemManager.getItems(itemIds);
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
        return false;
    }

    @Override
    public boolean setItemsQuestReward(List<ItemStack> items) {
        return false;
    }

    @Override
    public boolean setQuestDeleted() {
        this.mQuest.setIsDelete(true);
        return this.mQuest.updateQuest();
    }


}