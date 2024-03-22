package org.ardal.api.players;

import java.util.List;

public interface PlayerInfo {
    /*
            GETTER
     */



    /**
     * Get the player uuid
     *
     * @return player uuid
     */
    String getUuid();

    /**
     * Get the player name
     *
     * @return player name
     */
    String getName();

    /**
     * Get the adventure level of the player
     *
     * @return adventure level
     */
    int getAdventureLevel();

    /**
     * get player quest cooldown
     *
     * @return the cooldown of the player
     */
    int getQuestCooldown();

    /**
     * Get the player's active quests
     *
     * @return list of quest names
     */
    List<String> getPlayerActiveQuests();

    /**
     * Get the player's finished quests
     *
     * @return list of quest names
     */
    List<String> getPlayerFinishedQuests();



    /*
            SETTER
     */



    /**
     * Set the adventure level of a player
     *
     * @param level to set
     * @return true on success
     */
    boolean setAdventureLevel(int level);

    /**
     * Set quest cooldown to a player
     *
     * @param minutes of the cooldown (from now)
     * @return true on success
     */
    boolean setQuestCooldown(int minutes);

    /**
     * Clear the quest cooldown of a player
     *
     * @return true on success
     */
    boolean clearQuestCooldown();

    /**
     * Add an active quest for a player
     *
     * @param questName of the quest
     * @return true in success to add active quest
     */
    boolean addPlayerActiveQuest(String questName);

    /**
     * Add a finished quest for a player
     *
     * @param questName of the quest
     * @return true in success to add finished quest
     */
    boolean addPlayerFinishedQuest(String questName);

    /**
     * Remove quest for a player
     *
     * @param questName of the quest
     * @return true in success to remove quest
     */
    boolean removeQuest(String questName);
}
