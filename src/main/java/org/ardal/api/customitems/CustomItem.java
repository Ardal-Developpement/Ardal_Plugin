package org.ardal.api.customitems;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface CustomItem {
    ItemStack getItemByUUID(CommandSender sender, UUID id);

    ItemStack getItemByStrId(CommandSender sender, String id);

    List<ItemStack> getItemsByUUID(CommandSender sender, List<UUID> ids);

    List<ItemStack> getItemsByStrId(CommandSender sender, List<String> ids);

    UUID addItem(CommandSender sender, ItemStack item);

    boolean removeItem(CommandSender sender, String id);

    boolean removeItem(CommandSender sender, UUID id);

    boolean removeItem(CommandSender sender, ItemStack item);

    List<ItemStack> getItemsRange(CommandSender sender, int startIndex, int length);
}
