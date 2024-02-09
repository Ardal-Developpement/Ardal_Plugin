package org.ardal.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerUtils {
    public static void giveItemStackToPlayer(ItemStack itemStack, Player player){
        PlayerInventory playerInventory = player.getInventory();
        if(playerInventory.firstEmpty() != -1) {
            playerInventory.addItem(itemStack);
        } else {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        }
    }

}
