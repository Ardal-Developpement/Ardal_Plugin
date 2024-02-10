package org.ardal.api.quests;

import org.ardal.objects.QuestObj;
import org.bukkit.inventory.ItemStack;

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
     * Get the quest book from the quest db
     *
     * @param questName of the quest to get
     * @return quest book
     */
    ItemStack getQuestBook(String questName);

    /**
     * Get the items request by the quest
     *
     * @param questName of the quest
     * @return items quest request
     */
    List<ItemStack> getItemsQuestRequest(String questName);

    /**
     * Get the items reward by the quest
     *
     * @param questName of the quest
     * @return items quest reward
     */
    List<ItemStack> getItemQuestReward(String questName);

    List<String> getItemsQuestRequestId(String questName);

    List<String> getItemQuestRewardId(String questName);

    List<QuestObj> getAllQuestObj();

    List<String> getAllQuestNames();
}
