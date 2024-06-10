package org.ardal.inventories;

import org.ardal.Ardal;
import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class CIWithBackBtn extends CustomInventory implements CellCallBack {
    private final String parentName;
    private final int backBtnSlot;

    public CIWithBackBtn(String title, int size, Player player, String parentName) {
        super(title, size + 9, player); // reserved the last line for back btn

        if(size > 45) {
            throw new RuntimeException("CIWithBackBtn max size is 45!");
        }

        this.parentName = parentName;
        this.backBtnSlot = this.getSize() - 9;

        this.setCell(new CICell(
                this.getBackBtnItem(),
                this.backBtnSlot,
                null,
                null,
                this,
                null
        ));
    }

    public abstract void onItemsClick(InventoryClickEvent event);

    public abstract void onBackBtnClick(InventoryClickEvent event);

    @Override
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) {
            return;
        }

        if(event.getSlot() <= 45) {
            this.onItemsClick(event);
        } else if(event.getSlot() == this.backBtnSlot) {
            this.closeInventory();
            this.onBackBtnClick(event);
        }
    }

    public void buildCarousel(List<ItemStack> items, CICell cellTemplate) {
        if(items.size() > 45) {
            Ardal.getInstance().getLogger().severe("CIWithBackBtn max size is 45!");
        }

        for(int i = 0; i < items.size() && i < 45; i++) {
            this.setCell(new CICell(
                    items.get(i),
                    i++,
                    cellTemplate.getOnRightClickCB(),
                    cellTemplate.getOnRightShieftClickCB(),
                    cellTemplate.getOnLeftClickCB(),
                    cellTemplate.getOnLeftShieftClickCB()
            ));
        }
    }

    private ItemStack getBackBtnItem() {
        return ItemUtils.QuickItemSet(
                Material.SOUL_CAMPFIRE,
                this.parentName.isEmpty() ? "Quit" : this.parentName
        );
    }
}
