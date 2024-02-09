package org.ardal.api.quests;

import org.ardal.objects.QuestObj;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface QuestInfo {
    /**
     * Add a quest in quest db
     *
     * @param sender who sent command
     * @param book of the quest
     * @param itemsRequest to give to validate the quest
     * @param itemsReward to give to the player when quest validated
     * @return true in success to add quest
     */
    boolean addQuest(CommandSender sender, ItemStack book, List<ItemStack> itemsRequest, List<ItemStack> itemsReward);

    /**
     * Remove a quest from the quest db
     *
     * @param sender who sent command
     * @param questName of the quest to remove
     * @return true in success
     */
    boolean removeQuest(CommandSender sender, String questName);

    /**
     * Get the quest book from the quest db
     *
     * @param sender who sent command
     * @param questName of the quest to get
     * @return quest book
     */
    ItemStack getQuestBook(CommandSender sender, String questName);

    /**
     * Get the items request by the quest
     *
     * @param sender who sent command
     * @param questName of the quest
     * @return items quest request
     */
    List<ItemStack> getItemsQuestRequest(CommandSender sender, String questName);

    /**
     * Get the items reward by the quest
     *
     * @param sender who sent command
     * @param questName of the quest
     * @return items quest reward
     */
    List<ItemStack> getItemQuestReward(CommandSender sender, String questName);

    List<QuestObj> getAllQuestObj(CommandSender sender);
}
