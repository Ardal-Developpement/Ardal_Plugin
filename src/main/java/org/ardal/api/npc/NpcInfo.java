package org.ardal.api.npc;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public interface NpcInfo {

    /**
     * Remove a npc (if exist)
     *
     * @param id of the npc
     * @return true on success
     */
    boolean destroyNpc(UUID id);

    /**
     * If false, npc can be access only with perm.
     *
     * @param id of the npc
     * @param state of npc visibility
     * @return true on success
     */
    boolean setVisibleNpc(UUID id, boolean state);

    /**
     * Rename a npc
     *
     * @param id of the npc
     * @param newName of the npc
     * @return true on success
     */
    boolean setNpcName(UUID id, String newName);

    /**
     * Create a new npc template, which can then be invoked
     *
     * @param name of the npc
     * @param type of the npc
     * @return true on success
     */
    boolean createNewNpc(String name, CustomNpcType type, Location location);

    /**
     * Delete a npc template
     *
     * @param id of the npc
     * @return true on success
     */
    boolean deleteNpc(UUID id);

    /**
     * Get all saved id of the npc type
     *
     * @param type of the npc
     * @return list of npc id
     */
    List<UUID> getAllNpcIdByTypeSaved(CustomNpcType type);

    /**
     * Get all saved npc id
     *
     * @return list of npc id
     */
    List<UUID> getAllNpcIdSaved();

    /**
     * Check if a npc id exist in db.
     *
     * @param id of the npc
     * @return true if exist
     */
    boolean isNpcExist(UUID id);
}
