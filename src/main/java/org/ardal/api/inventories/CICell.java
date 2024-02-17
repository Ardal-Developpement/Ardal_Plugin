package org.ardal.api.inventories;

import org.ardal.api.inventories.callback.CellCallBack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CICell {
    private static final int NB_CELL_BY_LINE = 9;

    private final CellCallBack onRightClickCB;
    private final CellCallBack onRightShieftClickCB;
    private final CellCallBack onLeftClickCB;
    private final CellCallBack onLeftShieftClickCB;

    private ItemStack item;
    private int slot;




    public CICell(ItemStack item, int slot,
                  @Nullable CellCallBack onRightClickCB,
                  @Nullable CellCallBack onRightShieftClickCB,
                  @Nullable CellCallBack onLeftClickCB,
                  @Nullable CellCallBack onLeftShieftClickCB)
    {
        this.item = item;
        this.slot = slot;

        this.onRightClickCB = onRightClickCB;
        this.onRightShieftClickCB = onRightShieftClickCB;
        this.onLeftClickCB = onLeftClickCB;
        this.onLeftShieftClickCB = onLeftShieftClickCB;
    }

    public CICell(ItemStack item, int x, int y,
                  @Nullable CellCallBack onRightClickCB,
                  @Nullable CellCallBack onRightShieftClickCB,
                  @Nullable CellCallBack onLeftClickCB,
                  @Nullable CellCallBack onLeftShieftClickCB)
    {
        this.item = item;
        this.slot = y * NB_CELL_BY_LINE + x;

            this.onRightClickCB = onRightClickCB;
        this.onRightShieftClickCB = onRightShieftClickCB;
        this.onLeftClickCB = onLeftClickCB;
        this.onLeftShieftClickCB = onLeftShieftClickCB;
    }

    public CICell(ItemStack item, int slot){
        this.item = item;
        this.slot = slot;

        this.onRightClickCB = null;
        this.onRightShieftClickCB = null;
        this.onLeftClickCB = null;
        this.onLeftShieftClickCB = null;
    }

    public void onCellClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if(event.isRightClick()){
            if(event.isShiftClick()){
                doCallBack(this.onRightShieftClickCB, event);
            } else {
                doCallBack(this.onRightClickCB, event);
            }
        } else {
            if(event.isShiftClick()){
                doCallBack(this.onLeftShieftClickCB, event);
            } else {
                doCallBack(this.onLeftClickCB, event);
            }
        }
    }

    private void doCallBack(@Nullable CellCallBack callBack, InventoryClickEvent event){
        if(callBack != null) { callBack.onClick(event); }
    }


    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return this.slot;
    }
}
