package org.ardal.inventories;

import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class CICarousel extends CustomInventory implements CellCallBack {
    private final List<ItemStack> items;
    private CICell cellTemplate;
    private int currentStartIndex;
    private  int showedRange;

    public CICarousel(String title, int size, Player player, List<ItemStack> items) {
        super(title, size, player);

        this.items = items;
        this.currentStartIndex = 0;
        this.showedRange = size - 9; //keep the last line
    }

    public abstract void onItemsClick(InventoryClickEvent event);

    public CICarousel buildCarousel(CICell cellTemplate){
        this.cellTemplate = cellTemplate;
        this.showPage(true);
        this.setPreviousPageItem();
        this.setNextPageItem();
        return this;
    }

    private void showPage(boolean nextPage) {
        int newIndex;
        if (nextPage) {
            newIndex = this.currentStartIndex + showedRange;
            if (newIndex >= this.items.size()) {
                newIndex = 0;
            }
        } else {
            newIndex = this.currentStartIndex - this.showedRange;
            if (newIndex < 0) {
                newIndex = this.items.size() - 1 - this.showedRange;
                if(newIndex < 0){
                    newIndex = 0;
                }
            }
        }

        int slot = 0;
        for (int i = this.currentStartIndex; i < this.items.size() && slot < this.showedRange; i++) {
            this.setCell(new CICell(this.items.get(i),
                    slot,
                    this.cellTemplate.getOnRightClickCB(),
                    this.cellTemplate.getOnRightShieftClickCB(),
                    this.cellTemplate.getOnLeftClickCB(),
                    this.cellTemplate.getOnLeftShieftClickCB()
            ));
            slot++;
        }
        this.currentStartIndex = newIndex;

        //clean inventory
        for(; slot < this.showedRange; slot++){
            this.clearCell(slot);
        }
    }

    private void setNextPageItem(){
        ItemStack item = new ItemStack(Material.CAMPFIRE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Next page");
        item.setItemMeta(meta);

        this.setCell(new CICell(
                item,
                5, this.getSize() / 9  - 1,
                null,
                null,
                this,
                null
        ));
    }

    private void setPreviousPageItem(){
        ItemStack item = new ItemStack(Material.SOUL_CAMPFIRE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Previous page");

        item.setItemMeta(meta);
        this.setCell(new CICell(
                item,
                3, this.getSize() / 9  - 1,
                null,
                null,
                this,
                null
        ));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null) { return; }

        if(event.getSlot() < this.getSize() - 9){
            this.onItemsClick(event);
            return;
        }

        switch (item.getType()) {
            case CAMPFIRE:
                this.showPage(true);
                break;
            case SOUL_CAMPFIRE:
                this.showPage(false);
                break;
        }
    }

    @Override
    public void onCIClick(InventoryClickEvent event){
        event.setCancelled(true);
        CICell cell = this.getCell(event.getSlot());
        if(cell != null){
            cell.onCellClick(event);
        }
    }
}
