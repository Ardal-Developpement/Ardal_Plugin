package org.ardal.api.players;

import org.ardal.objects.PlayerObj;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PlayerManagerInfo {
    /*
            GETTER
     */


    /**
     * Get player object by name
     *
     * @param playerName of the player
     * @return player object if found
     */
    @Nullable
    PlayerObj getPlayerObj(String playerName);

    /**
     * Check if a player exist in the db
     *
     * @param playerName of the player
     * @return true if the player exist in db
     */
    boolean getIsPlayerExist(String playerName);

    /**
     * Get all player names save in the db
     *
     * @return player names
     */
    List<String> getAllPlayerNames();



    /*
            SETTER
     */


    /**
     * Save a new player in the db
     *
     * @param player to save
     * @return true on success
     */
    boolean saveNewPlayer(Player player);
}
