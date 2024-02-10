package org.ardal.api.customitems;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface CustomItem {
    ItemStack getItemByUUID(UUID id);

    ItemStack getItemByStrId(String id);

    List<ItemStack> getItemsByUUID(List<UUID> ids);

    List<ItemStack> getItemsByStrId(List<String> ids);

    UUID addItem(ItemStack item);

    boolean removeItem(String id);

    boolean removeItem(UUID id);

    boolean removeItem(ItemStack item);

    List<ItemStack> getItemsRange(int startIndex, int length);
}
