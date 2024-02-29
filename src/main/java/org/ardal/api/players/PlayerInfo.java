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
    boolean addAdventureLevel(OfflinePlayer player, long level);

    /**
     * Get the adventure level of a player
     *
     * @param player to get adventure level
     * @return adventure level
     */
    long getAdventureLevel(OfflinePlayer player);

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
     * Remove an active quest for a player
     *
     * @param player to remove the active quest
     * @param questName of the quest
     * @return true in success to remove active quest
     */
    boolean removePlayerActiveQuest(OfflinePlayer player, String questName);

    /**
     * Remove a finished quest for a player
     *
     * @param player to remove the finished quest
     * @param questName of the quest
     * @return true in success to remove finished quest
     */
    boolean removePlayerFinishedQuest(OfflinePlayer player, String questName);

    /**
     * Get if a player info is saved
     *
     * @param player to check
     * @return true in player register in the db, else false
     */
    boolean isPlayerRegistered(OfflinePlayer player);
}
