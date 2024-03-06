package org.ardal.inventories;

import org.ardal.api.inventories.CustomInventory;
import org.bukkit.entity.Player;

public class CIPickUp extends CustomInventory {


    public CIPickUp(String title, int size, Player player) {
        super(title, size, player);
    }
}
