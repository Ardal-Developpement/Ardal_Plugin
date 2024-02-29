package org.ardal.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class PlayerUtils {
    public static void giveItemStackToPlayer(ItemStack itemStack, Player player){
        PlayerInventory playerInventory = player.getInventory();
        if(playerInventory.firstEmpty() != -1) {
            playerInventory.addItem(itemStack);
        } else {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        }
    }

    public static boolean removeItemStackToPlayerInvsee(ItemStack item, Player player){
        PlayerInventory pI = player.getInventory();

        for(int i = 0; i < pI.getSize(); i++){
            ItemStack currentItem = pI.getItem(i);

            if(currentItem != null && currentItem.isSimilar(item)){
                if(currentItem.getAmount() > item.getAmount()){
                    currentItem.setAmount(currentItem.getAmount() - item.getAmount());
                    return true;
                } else if (currentItem.getAmount() == item.getAmount()) {
                    pI.setItem(i, null);
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean removeItemsToPlayer(List<ItemStack> items, Player player){
        for(ItemStack item : items){
            if(!PlayerUtils.removeItemStackToPlayerInvsee(item, player)){
                return false;
            }
        }

        return true;
    }

}
