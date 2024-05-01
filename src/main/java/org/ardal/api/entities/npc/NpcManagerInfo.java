package org.ardal.api.entities.npc;

import org.ardal.objects.NpcObj;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface NpcManagerInfo {
    /*
            GETTER
     */


    /**
     * Get the npc object
     *
     * @param npcUuid of the npc
     * @return npc object (null if not registered)
     */
    @Nullable
    NpcObj getRegisteredNpcByUuid(String npcUuid);

    /**
     * Get all saved npc id
     *
     * @return list of npc id
     */
    List<String> getAllNpcUuids();

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
     * Give to the player, the npc management tool.
     *
     * @param player to give item
     * @return true on success
     */
    boolean giveManagementToolToPlayer(Player player);
}
