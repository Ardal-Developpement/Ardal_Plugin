package org.ardal.api.npc;

import org.bukkit.Location;

import java.util.List;

public interface NpcInfo {
    /**
     * Invoke a npc
     *
     * @param name of the npc
     * @return true on success
     */
    boolean invokeNpc(String name);

    /**
     * Remove a npc (if exist)
     *
     * @param name of the npc
     * @return true on success
     */
    boolean destroyNpc(String name);

    /**
     * Get the location of the npc (if exist)
     *
     * @param name of the npc
     * @return the location of the npc, null if the npc is not invoked
     */
    Location getNpcLocation(String name);

    /**
     * If false, npc can be access only with perm.
     *
     * @param state of npc visibility
     * @return true on success
     */
    boolean setVisibleNpc(String name, boolean state);

    /**
     * Create a new npc template, which can then be invoked
     *
     * @param name of the npc
     * @param type of the npc
     * @return true on success
     */
    boolean createNewNpc(String name, CustomNpcType type);

    /**
     * Delete a npc template
     *
     * @param name of the npc
     * @return true on success
     */
    boolean deleteNpc(String name);

    /**
     * Get all registered names of the npc type
     *
     * @param type of the npc
     * @return list of npc names
     */
    List<String> getAllNpcNamesByType(CustomNpcType type);

    /**
     * Get all registered npc names
     *
     * @return list of npc names
     */
    List<String> getAllNpcNames();
}
