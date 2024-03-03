package org.ardal.db;

import org.ardal.api.db.YamlDBStruct;
import org.ardal.objects.CustomItemObj;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomItemDB extends YamlDBStruct {
    private static final String DB_FILE_NAME = "CustomItem.yml";
    public CustomItemDB(Path pluginDirPath) {

        super(pluginDirPath, DB_FILE_NAME);
    }

    public String addItem(ItemStack item){
        CustomItemObj customItemObj = new CustomItemObj(item);
        customItemObj.addItem(this.getDB());
        this.saveDB();

        return customItemObj.getHashId();
    }

    public boolean removeItem(String hashId) {
        ConfigurationSection containerSection = this.getDB().getConfigurationSection(hashId);

        if(containerSection == null){
            return false;
        }

        int itemNbUsage = containerSection.getInt("nbUsage");
        if(itemNbUsage - 1 == 0){
            this.getDB().set(hashId, null);
        } else {
            containerSection.set("nbUsage", itemNbUsage - 1);
        }

        this.saveDB();
        return true;
    }

    @Nullable
    public ItemStack getItem(String hashId){
        ConfigurationSection itemSection = this.getDB().getConfigurationSection(hashId);
        if(itemSection == null) { return null; }

        return new CustomItemObj(itemSection).getItem();
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
