package org.ardal.inventories.implementations;

import org.ardal.inventories.CICallBack;
import org.ardal.inventories.CISize;
import org.ardal.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CIDropBox extends CustomInventory {
    private final CICallBack onCloseCallBack;
    public CIDropBox(String title, CISize size, Player player, CICallBack onCloseCallBack){ //TODO add Nullable
        super(title, size, player);
        this.onCloseCallBack = onCloseCallBack;
    }
    @Override
    public void onCIClose(InventoryCloseEvent event) {
        this.onCloseCallBack.executeCICallBack(this);
    }

    @Override
    public void onCIClick(InventoryClickEvent event) {

    }
}
