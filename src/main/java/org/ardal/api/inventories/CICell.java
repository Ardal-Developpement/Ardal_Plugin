package org.ardal.api.inventories;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CICell {
    private final Inventory inventory;
    private int slot;

    public CICell(Inventory inventory, int slot){
        this.inventory = inventory;
        this.slot = slot;
    }

    public ItemStack getItem() {
        return this.inventory.getItem(this.slot);
    }

    public void setItem(ItemStack item) {
        this.inventory.setItem(this.getSlot(), item);
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return this.slot;
    }

    public boolean isEmpty(){
        return this.inventory.getItem(this.slot) == null;
    }
}
