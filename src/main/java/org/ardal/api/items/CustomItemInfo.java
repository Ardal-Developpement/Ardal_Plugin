package org.ardal.api.items;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface CustomItemInfo {
    /**
     * Get a custom item by his UUID
     *
     * @param customItemUUID of the custom item
     * @return custom item
     */
    ItemStack getCustomItem(UUID customItemUUID);

    /**
     * Get a custom item by his name
     *
     * @param customItemName of the custom item
     * @return first custom item with this name
     */
    @Deprecated
    ItemStack getCustomItem(String customItemName);

    /**
     * Add a custom item in the custom item db
     *
     * @param customItem to add
     * @return true in success
     */
    boolean addCustomItem(ItemStack customItem);

    /**
     * Remove a custom item in the custom item db
     *
     * @param customItemUUID to remove
     * @return true in success
     */
    boolean removeCustomItemByUUID(UUID customItemUUID);

    /**
     * Remove the first custom item in the custom item db with this name
     *
     * @param customItemName to remove
     * @return true in success
     */
    @Deprecated
    boolean removeCustomItemByName(String customItemName);

    /**
     * Remove a custom item in the custom item db
     *
     * @param customItem to remove
     * @return true in success
     */
    boolean removeCustomItemByItem(ItemStack customItem);
}
