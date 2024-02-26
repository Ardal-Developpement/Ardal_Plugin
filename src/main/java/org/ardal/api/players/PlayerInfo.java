package org.ardal.api.players;

import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

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
     * @return list of UUID quests
     */
    List<UUID> getPlayerActiveQuests(OfflinePlayer player);

    /**
     * Get the player's finished quests
     *
     * @param player to get finished quests
     * @return list of UUID quests
     */
    List<UUID> getPlayerFinishedQuests(OfflinePlayer player);

    /**
     * Add an active quest for a player
     *
     * @param player to add the active quest
     * @return true in success to add active quest
     */
    boolean addPlayerActiveQuest(OfflinePlayer player, UUID questUUID);

    /**
     * Remove an active quest for a player
     *
     * @param player to remove the active quest
     * @return true in success to remove active quest
     */
    boolean removePlayerActiveQuest(OfflinePlayer player, UUID questUUID);

    /**
     * Remove a finished quest for a player
     *
     * @param player to remove the finished quest
     * @return true in success to remove finished quest
     */
    boolean removePlayerFinishedQuest(OfflinePlayer player, UUID questUUID);

    /**
     * Get if a player info is saved
     *
     * @param player to check
     * @return true in player register in the db, else false
     */
    boolean isPlayerRegistered(OfflinePlayer player);
}
