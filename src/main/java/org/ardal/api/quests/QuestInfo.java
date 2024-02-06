package org.ardal.api.quests;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;
import java.util.UUID;

public interface QuestInfo {
    /**
     * Add a quest in quest db
     *
     * @param bookMeta of the quest book
     * @param itemsRequest to give to validate the quest
     * @param itemsReward to give to the player when quest validated
     * @return true in success to add quest
     */
    boolean addQuest(BookMeta bookMeta, List<UUID> itemsRequest, List<UUID> itemsReward);

    /**
     * Add a quest in quest db
     *
     * @param bookMeta of the quest book
     * @param synopsis of the quest
     * @param itemsRequest to give to validate the quest
     * @param itemsReward to give to the player when quest validated
     * @return true in success to add quest
     */
    @Deprecated
    boolean addQuest(BookMeta bookMeta, String synopsis, List<ItemStack> itemsRequest, List<ItemStack> itemsReward);

    /**
     * Remove a quest from the quest db
     *
     * @param questUUID of the quest to remove
     * @return true in success
     */
    boolean removeQuest(UUID questUUID);

    /**
     * Get the quest book from the quest db
     *
     * @param questUUID of the quest to get
     * @return quest book
     */
    ItemStack getQuestBook(UUID questUUID);

    /**
     * Get the items request by the quest
     *
     * @param questUUID of the quest
     * @return items quest request
     */
    List<ItemStack> getItemsQuestRequest(UUID questUUID);

    /**
     * Get the items reward by the quest
     *
     * @param questUUID of the quest
     * @return items quest reward
     */
    List<ItemStack> getItemQuestReward(UUID questUUID);
}
