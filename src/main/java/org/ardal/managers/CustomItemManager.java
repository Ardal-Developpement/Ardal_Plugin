package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.customitems.CustomItem;
import org.ardal.api.managers.ArdalManager;
import org.ardal.db.CustomItemDB;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomItemManager implements CustomItem, ArdalManager {
    private final CustomItemDB customItemDB;

    public CustomItemManager() {
        this.customItemDB = new CustomItemDB(Ardal.getInstance().getDataFolder().toPath().toAbsolutePath());
    }

    public CustomItemDB getCustomItemDB() {
        return customItemDB;
    }
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        this.customItemDB.saveDB();
    }

    @Override
    public ItemStack getItem(String id) {
        return this.customItemDB.getItem(id).clone();
    }

    @Override
    public List<ItemStack> getItems(List<String> ids) {
        List<ItemStack> items = new ArrayList<>();
        for(String id : ids){
            items.add(this.getItem(id));
        }

        return items;
    }

    @Override
    public String addItem(ItemStack item) {
        if(item == null){
            return null;
        }

        return this.customItemDB.addItem(item);
    }

    @Override
    public boolean removeItem(String id) {
        return this.customItemDB.removeItem(id);
    }

    @Override
    public List<ItemStack> getItemsRange(int startIndex, int length) {
        return this.customItemDB.getItemsRange(startIndex, length);
    }
}
