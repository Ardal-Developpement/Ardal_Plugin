package org.ardal.api.quests;

import org.ardal.objects.QuestObj;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface QuestInfo {
    /**
     * Add a quest in quest db
     *
     * @param book of the quest
     * @param itemsRequestId to give to validate the quest
     * @param itemsRewardId to give to the player when quest validated
     * @return true in success to add quest
     */
    boolean addQuest(ItemStack book, List<ItemStack> itemsRequestId, List<ItemStack> itemsRewardId);

    /**
     * Remove a quest from the quest db
     *
     * @param questName of the quest to remove
     * @return true in success
     */
    boolean removeQuest(String questName);

    /**
     * Get quest book from the quest db
     *
     * @param questName of the quest to get
     * @return quest book
     */
    ItemStack getQuestBook(String questName);

    /**
     * Get items reward by the quest
     *
     * @param questName of the quest
     * @return items quest request
     */
    List<ItemStack> getItemsQuestRequest(String questName);

    /**
     * Get items reward by the quest
     *
     * @param questName of the quest
     * @return items quest reward
     */
    List<ItemStack> getItemQuestReward(String questName);

    /**
     * Get item ids request by the quest
     *
     * @param questName of the quest
     * @return list of item ids
     */
    List<String> getItemsQuestRequestId(String questName);

    /**
     * Get item ids reward by the quest
     *
     * @param questName of the quest
     * @return list of item ids
     */
    List<String> getItemQuestRewardId(String questName);

    /**
     * Get all quest saved (sorted by names)
     *
     * @return list of quest objs
     */
    List<QuestObj> getAllQuestObj();

    /**
     * Get all quest names (sorted by names)
     *
     * @return list of quest names
     */
    List<String> getAllQuestNames();

    /**
     * Set the quest activity
     *
     * @param questName of the quest
     * @param state to set
     * @return true on success
     */
    @Nullable
    Boolean setQuestActivity(String questName, boolean state);

    /**
     * Set quest deleted (for safe delete)
     *
     * @param questName of the quest
     * @return true on success
     */
    @Nullable
    Boolean setQuestDeleted(String questName);

    /**
     * Set a new quest synopsis
     *
     * @param questName of the quest
     * @param synopsis new synopsis
     */
    void setQuestSynopsis(String questName, @Nullable String synopsis);

    /**
     * Get the quest activity
     *
     * @param questName of the quest
     * @return true on success
     */
    @Nullable
    Boolean getQuestActivity(String questName);

    /**
     * Get the quest delete state
     *
     * @param questName of the quest
     * @return state
     */
    boolean getQuestDeleteState(String questName);

    /**
     * Return if the quest exist
     *
     * @param questName of the quest
     * @return true if the quest exist
     */
    boolean questExist(String questName);

    /**
     * Get the synopsis of the quest
     *
     * @param questName of the quest
     * @return quest synopsis
     */
    @Nullable
    String getQuestSynopsis(String questName);


}
