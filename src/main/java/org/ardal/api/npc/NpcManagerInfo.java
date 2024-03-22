package org.ardal.api.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface NpcManagerInfo {
    /*
            GETTER
     */



    /**
     * Get all saved npc id
     *
     * @return list of npc id
     */
    List<String> getAllNpcIdSaved();

    /**
     * Check if a npc id exist in db.
     *
     * @param npcUuid of the npc
     * @return true if exist
     */
    boolean getIsNpcExist(String npcUuid);



    /*
            SETTER
     */



    /**
     * Create a new npc template, which can then be invoked
     *
     * @param name of the npc
     * @param type of the npc
     * @return true on success
     */
    boolean createNewNpc(String name, CustomNpcType type, Location location);

    /**
     * Give to the player, the npc management tool.
     *
     * @param player to give item
     * @return true on success
     */
    boolean giveManagementToolToPlayer(Player player);
}
