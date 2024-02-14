package org.ardal.inventories;

import org.ardal.api.inventories.CICallBack;
import org.ardal.api.inventories.CISize;
import org.ardal.api.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.Nullable;

public class CIDropBox extends CustomInventory {
    private final CICallBack onCloseCallBack;
    private final CICallBack onClickCallBack;
    public CIDropBox(String title, CISize size, Player player, @Nullable CICallBack onCloseCallBack, @Nullable CICallBack onClickCallBack){
        super(title, size, player);
        this.onCloseCallBack = onCloseCallBack;
        this.onClickCallBack = onClickCallBack;
    }
    @Override
    public void onCIClose(InventoryCloseEvent event) {
        if(this.onCloseCallBack == null) { return; }
        this.onCloseCallBack.executeCICallBack(this);
        this.unregisterInventory();
    }

    @Override
    public void onCIClick(InventoryClickEvent event) {
        if(this.onClickCallBack == null) { return; }
        this.onClickCallBack.executeCICallBack(this);
    }
}
