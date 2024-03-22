package org.ardal.api.npc;

import org.bukkit.Location;
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
     * Get npc visible state
     *
     * @return npc visible state
     */
    boolean getIsVisible();

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
    CustomNpcType getNpcType();



    /*
            SETTER
     */


    /**
     * Set the npc name
     *
     * @param newName of the npc
     * @return true on success
     */
    boolean setName(@NotNull String newName);

    /**
     * Set the visible state of the npc
     *
     * @param visibleState of the npc
     * @return true on success
     */
    boolean setIsVisible(boolean visibleState);

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
