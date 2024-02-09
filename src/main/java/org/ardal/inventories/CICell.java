package org.ardal.inventories;

import org.bukkit.inventory.ItemStack;

public class CICell {
    private ItemStack item;
    private int slot;

    public CICell(ItemStack item, int slot){
        this.item = item;
        this.slot = slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isEmpty(){
        return this.item == null;
    }
}
