package org.ardal.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static ItemStack QuickItemSet(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack QuickItemEnchantSet(ItemStack item, Enchantment ench, int level, boolean cloneItem) {
        ItemStack itemCp = item;
        if(cloneItem)  {
            itemCp = item.clone();
        }
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(ench, level, true);
        item.setItemMeta(meta);

        return itemCp;
    }
}
