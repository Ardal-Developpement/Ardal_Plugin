package org.ardal.db;

import org.ardal.api.db.YamlDBStruct;
import org.bukkit.inventory.ItemStack;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomItemDB extends YamlDBStruct {
    private static final String DB_FILE_NAME = "CustomItem.yml";
    public CustomItemDB(Path pluginDirPath) {

        super(pluginDirPath, DB_FILE_NAME);
    }

    public UUID addItem(ItemStack item){
        UUID id = UUID.randomUUID();
        this.getDB().set(id.toString(), item);
        this.saveDB();
        return id;
    }

    public ItemStack getItem(String id){
        return this.getDB().getItemStack(id);
    }

    public ItemStack getItem(UUID id){
        return this.getItem(id.toString());
    }

    public boolean removeItem(String id) {
        if (this.getItem(id) != null) {
            this.getDB().set(id.toString(), null);
            return true;
        }

        return false;
    }

    public boolean removeItem(UUID id) {
        return this.removeItem(id.toString());
    }

    public List<ItemStack> getItemsRange(int startIndex, int length) {
        List<ItemStack> items = new ArrayList<>();
        List<String> keys = this.getKeySet();

        int maxIndex;
        if(startIndex + length > keys.size() || length == -1){
            maxIndex = keys.size();
        } else {
            maxIndex = startIndex + length;
        }

        for(int i = startIndex; i < maxIndex; i++){
            items.add(this.getItem(keys.get(i)));
        }

        return items;
    }
}
