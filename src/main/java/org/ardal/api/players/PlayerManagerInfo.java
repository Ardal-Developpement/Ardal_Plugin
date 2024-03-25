package org.ardal.api.players;

import org.ardal.objects.PlayerObj;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PlayerManagerInfo {
    /*
            GETTER
     */



    /**
     * Check if a player exist in the db
     *
     * @param player of the player
     * @return true if the player exist in db
     */
    boolean getIsPlayerExist(OfflinePlayer player);

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
    boolean saveNewPlayer(OfflinePlayer player);
}
