package org.ardal.api.quests;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface QuestInfo {
    /*
            GETTER
     */



    /**
     * Get quest book from the quest db
     *
     * @return quest book
     */
    ItemStack getQuestBook();

    /**
     * Get the synopsis of the quest
     *
     * @return quest synopsis
     */
    @Nullable
    String getQuestSynopsis();

    /**
     * Get items reward by the quest
     *
     * @return items quest request
     */
    List<ItemStack> getItemsQuestRequest();

    /**
     * Get items reward by the quest
     *
     * @return items quest reward
     */
    List<ItemStack> getItemQuestReward();

    /**
     * Get the quest activity
     *
     * @return true on success
     */
    boolean getQuestActivity();

    /**
     * Get the quest delete state
     *
     * @return state
     */
    boolean getQuestIsDeleted();



    /*
            SETTER
     */



    /**
     * Set a new quest book
     *
     * @param questBook of the quest
     * @return true on success
     */
    boolean setQuestBook(ItemStack questBook);

    /**
     * Set a new quest synopsis
     *
     * @param synopsis new synopsis
     */
    boolean setQuestSynopsis(@Nullable String synopsis);

    /**
     * Set new items requested by the quest
     *
     * @param items request by the quest
     * @return true on success
     */
    boolean setItemsQuestRequest(List<ItemStack> items);

    /**
     * Set new items rewarded by the quest
     *
     * @param items reward by the quest
     * @return true on success
     */
    boolean setItemsQuestReward(List<ItemStack> items);

    /**
     * Set the quest activity
     *
     * @param state to set
     * @return true on success
     */
    boolean setQuestActivity(boolean state);

    /**
     * Set quest deleted (for safe delete)
     *
     * @return true on success
     */
    boolean setQuestDeleted();
}
