package org.ardal.callbacks.npc;

import org.ardal.Ardal;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.managers.CustomNPCManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class DeleteNpcCallBack implements CellCallBack {
    private final UUID npcId;

    public DeleteNpcCallBack(UUID npcId) {
        this.npcId = npcId;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(Ardal.getInstance().getManager(CustomNPCManager.class).deleteNpc(npcId)){
            player.sendMessage("Success to delete npc.");
        } else {
            player.sendMessage("Failed to delete npc.");
        }
    }
}
