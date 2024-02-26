package org.ardal.api.customitems;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface CustomItem {
    /**
     * Get an item saved by its id
     *
     * @param id of the item
     * @return item
     */
    ItemStack getItemByUUID(UUID id);

    /**
     * Get an item saved by its id
     *
     * @param id of item
     * @return item
     */
    ItemStack getItemByStrId(String id);

    /**
     * Get an items saved by its id
     *
     * @param ids of items
     * @return item list
     */
    List<ItemStack> getItemsByUUID(List<UUID> ids);

    /**
     * Get an items saved by its id
     *
     * @param ids of items
     * @return item list
     */
    List<ItemStack> getItemsByStrId(List<String> ids);

    /**
     * Save an item from the db
     *
     * @param item to add
     * @return id of added item
     */
    UUID addItem(ItemStack item);

    /**
     * Remove an item from the db
     *
     * @param id of the item to remove
     * @return true on success
     */
    boolean removeItem(String id);

    /**
     * Remove an item from the db
     *
     * @param id of the item to remove
     * @return true on success
     */
    boolean removeItem(UUID id);

    /**
     * Remove an item from the db
     *
     * @param item to remove
     * @return true on success
     */
    boolean removeItem(ItemStack item);

    /**
     * Get a list of items from db items range
     *
     * @param startIndex of items
     * @param length of list
     * @return items list
     */
    List<ItemStack> getItemsRange(int startIndex, int length);
}
