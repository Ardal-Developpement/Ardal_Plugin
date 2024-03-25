package org.ardal.api.quests;

import org.ardal.objects.QuestObj;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface QuestManagerInfo {
    /*
            GETTER
     */



    /**
     * Get all quest names (sorted by names)
     *
     * @param includeDeleted quests
     * @return list of quest names
     */
    List<String> getAllQuestNames(boolean includeDeleted);



    /*
            SETTER
     */


    /**
     * Add a quest in quest db
     *
     * @param book of the quest
     * @param itemsRequestId to give to validate the quest
     * @param itemsRewardId to give to the player when quest validated
     * @return true in success to add quest
     */
    boolean createNewQuest(ItemStack book, List<ItemStack> itemsRequestId, List<ItemStack> itemsRewardId);
}
