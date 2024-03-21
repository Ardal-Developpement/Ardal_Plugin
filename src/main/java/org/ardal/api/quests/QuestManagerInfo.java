package org.ardal.api.quests;

import org.ardal.objects.QuestObj;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface QuestManagerInfo {
    /**
     * Add a quest in quest db
     *
     * @param book of the quest
     * @param itemsRequestId to give to validate the quest
     * @param itemsRewardId to give to the player when quest validated
     * @return true in success to add quest
     */
    boolean createNewQuest(ItemStack book, List<ItemStack> itemsRequestId, List<ItemStack> itemsRewardId);

    /**
     * Get all quest names (sorted by names)
     *
     * @param includeDeleted quests
     * @return list of quest names
     */
    List<String> getAllQuestNames(boolean includeDeleted);

    /**
     * Return if the quest exist
     *
     * @param questName of the quest
     * @return true if the quest exist
     */
    boolean getIsQuestExist(String questName, boolean includeDeleted);

    /**
     * Get the quest by name
     *
     * @param questName of the quest
     * @param includeDeleted quests
     * @return quest obj (null if quest not found)
     */
    @Nullable
    QuestObj getQuestObj(String questName, boolean includeDeleted);

    /**
     * Get the quest by name (not include deleted quests)
     *
     * @param questName of the quest
     * @return quest obj (null if quest not found)
     */
    @Nullable
    QuestObj getQuestObj(String questName);
}
