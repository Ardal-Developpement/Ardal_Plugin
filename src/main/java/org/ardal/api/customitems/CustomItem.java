package org.ardal.api.customitems;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface CustomItem {

    /**
     * Get an item saved by its id
     *
     * @param hashId of item
     * @return item
     */
    ItemStack getItem(String hashId);


    /**
     * Get an items saved by its id
     *
     * @param hashIds of items
     * @return item list
     */
    List<ItemStack> getItems(List<String> hashIds);

    /**
     * Save an item from the db
     *
     * @param item to add
     * @return hash id of the added item
     */
    String addItem(ItemStack item);

    /**
     * Remove an item from the db
     *
     * @param hashId of the item to remove
     * @return true on success
     */
    boolean removeItem(String hashId);

    /**
     * Remove items from the db
     *
     * @param hashIds of items to remove
     * @return true on success
     */
    boolean removeItems(List<String> hashIds);

    /**
     * Get a list of items from db items range
     *
     * @param startIndex of items
     * @param length of list
     * @return items list
     */
    List<ItemStack> getItemsRange(int startIndex, int length);
}
