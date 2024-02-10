package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.customitems.CustomItem;
import org.ardal.api.managers.ArdalManager;
import org.ardal.db.CustomItemDB;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public ItemStack getItemByStrId(String id) {
        return this.customItemDB.getItem(id);
    }

    @Override
    public List<ItemStack> getItemsByUUID(List<UUID> ids) {
        List<ItemStack> items = new ArrayList<>();
        for(UUID id : ids){
            items.add(this.getItemByUUID(id));
        }

        return items;
    }

    @Override
    public List<ItemStack> getItemsByStrId(List<String> ids) {
        List<ItemStack> items = new ArrayList<>();
        for(String id : ids){
            items.add(this.getItemByStrId(id));
        }

        return items;
    }

    @Override
    public ItemStack getItemByUUID(UUID id) {
        return this.getItemByStrId(id.toString());
    }

    @Override
    public UUID addItem(ItemStack item) {
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
    public boolean removeItem(UUID id) {
        return this.removeItem(id.toString());
    }

    @Override
    public boolean removeItem(ItemStack item) {
        List<ItemStack> items = this.customItemDB.getItemsRange(0, -1);
        for(int i = 0; i < items.size(); i++){
            if(items.get(i) == item){
                return this.removeItem(this.getCustomItemDB().getKeySet().get(i));
            }
        }

        return false;
    }

    @Override
    public List<ItemStack> getItemsRange(int startIndex, int length) {
        return this.customItemDB.getItemsRange(startIndex, length);
    }
}
