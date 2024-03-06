package org.ardal.inventories;

import org.ardal.api.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CIPickUp extends CustomInventory {
    public CIPickUp(String title, int size, Player player, List<ItemStack> items) {
        super(title, size, player);
    }
}
