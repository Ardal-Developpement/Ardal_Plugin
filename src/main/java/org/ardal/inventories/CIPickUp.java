package org.ardal.inventories;

import org.ardal.api.inventories.CICell;
import org.ardal.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CIPickUp extends CICarousel {
    private final List<ItemStack> itemPickedUp;
    private final boolean pickedUpOnce;
    private final int nbItemPickedUpLimit;
    private int nbPickedUp;
    public CIPickUp(String title, int size, Player player, List<ItemStack> items, boolean pickUpOnce, int nbItemPickedUpLimit) {
        super(title, size, player);

        this.nbItemPickedUpLimit = nbItemPickedUpLimit;
        this.nbPickedUp = 0;
        this.pickedUpOnce = pickUpOnce;
        this.itemPickedUp = new ArrayList<>();

        this.buildCarousel(items, new CICell(null, -1,
                null,
                null,
                this,
                null)
        );
    }

    @Override
    public void onItemsClick(InventoryClickEvent event) {
        // Give item to player
        if(this.pickedUpOnce){
            if(this.itemPickedUp.contains(event.getCurrentItem())){
                event.getWhoClicked().sendMessage("You have already take this item.");
                return;
            }

            this.itemPickedUp.add(event.getCurrentItem());
        }

        PlayerUtils.giveItemStackToPlayer(event.getCurrentItem(), (Player) event.getWhoClicked());

        if(this.nbItemPickedUpLimit != -1 && this.nbPickedUp++ >= this.nbItemPickedUpLimit){
            this.closeInventory();
        }
    }
}
