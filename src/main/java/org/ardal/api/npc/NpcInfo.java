package org.ardal.api.npc;

import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

public interface NpcInfo {
    /*
            GETTER
     */


    /**
     * Get npc uuid
     *
     * @return npc uuid
     */
    String getUuid();

    /**
     * Get npc name
     *
     * @return npc name
     */
    String getName();

    /**
     * Get npc skin name
     *
     * @return npc skin name
     */
    String getSkinName();


    /**
     * Get npc location
     *
     * @return npc location
     */
    Location getLocation();

    /**
     * Get npc type
     *
     * @return npc type
     */
    NpcType getType();



    /*
            SETTER
     */



    /**
     * Set the npc uuid
     *
     * @param newUuid of the npc
     * @return true on success
     */
    boolean setUuid(@NotNull String newUuid);

    /**
     * Set the npc name
     *
     * @param newName of the npc
     * @return true on success
     */
    boolean setName(@NotNull String newName);

    /**
     * Set the npc skin name
     *
     * @param skinName of the npc skin
     * @param updateDB update skin name in the db
     */
    void setNpcSkin(@NotNull String skinName, boolean updateDB);

    /**
     * Set the npc location
     *
     * @param newLocation of the npc
     * @return true on success
     */
    boolean setLocation(@NotNull Location newLocation);

    /**
     * Delete the npc
     *
     * @return true on success
     */
    boolean deleteNpc();
}
