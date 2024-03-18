package org.ardal.api.players;

import org.bukkit.OfflinePlayer;

import java.util.List;

public interface PlayerInfo {
    /**
     * Add the adventure level of a player
     *
     * @param player to get adventure level
     * @param level to add to the player
     */
    boolean addAdventureLevel(OfflinePlayer player, int level);

    /**
     * Get the adventure level of a player
     *
     * @param player to get adventure level
     * @return adventure level
     */
    int getAdventureLevel(OfflinePlayer player);

    /**
     * Get the player's active quests
     *
     * @param player to get active quests
     * @return list of quest names
     */
    List<String> getPlayerActiveQuests(OfflinePlayer player);

    /**
     * Get the player's finished quests
     *
     * @param player to get finished quests
     * @return list of quest names
     */
    List<String> getPlayerFinishedQuests(OfflinePlayer player);

    /**
     * Add an active quest for a player
     *
     * @param player to add the active quest
     * @param questName of the quest
     * @return true in success to add active quest
     */
    boolean addPlayerActiveQuest(OfflinePlayer player, String questName);

    /**
     * Add an finished quest for a player
     *
     * @param player to add the finished quest
     * @param questName of the quest
     * @return true in success to add finished quest
     */
    boolean addPlayerFinishedQuest(OfflinePlayer player, String questName);

    /**
     * Remove quest for a player
     *
     * @param player to remove the finished quest
     * @param questName of the quest
     * @return true in success to remove quest
     */
    boolean removePlayerQuest(OfflinePlayer player, String questName);

    /**
     * Get if a player info is saved in the db
     *
     * @param player to check
     * @return true in player register in the db, else false
     */
    boolean isPlayerRegistered(OfflinePlayer player);

    /**
     * Set quest cooldown to a player
     *
     * @param player to set quest cooldown
     * @param minutes of the cooldown (from now)
     */
    void setQuestCooldown(OfflinePlayer player, int minutes);

    /**
     * Clear the quest cooldown of a player
     *
     * @param player to clear quest cooldown
     */
    void clearQuestCooldown(OfflinePlayer player);

    /**
     * get player quest cooldown
     *
     * @param player to get quest cooldown
     * @return the cooldown of the player
     */
    int getQuestCooldown(OfflinePlayer player);
}
