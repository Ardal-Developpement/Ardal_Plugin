package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.customitems.CustomItem;
import org.ardal.api.managers.ArdalManager;
import org.ardal.db.CustomItemDB;
import org.bukkit.command.CommandSender;
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
    public ItemStack getItemByStrId(CommandSender sender, String id) {
        ItemStack item = this.customItemDB.getItem(id);
        if(item == null){
            sender.sendMessage("Invalid id.");
        }

        return item;
    }

    @Override
    public List<ItemStack> getItemsByUUID(CommandSender sender, List<UUID> ids) {
        List<ItemStack> items = new ArrayList<>();
        for(UUID id : ids){
            items.add(this.getItemByUUID(sender, id));
        }

        return items;
    }

    @Override
    public List<ItemStack> getItemsByStrId(CommandSender sender, List<String> ids) {
        List<ItemStack> items = new ArrayList<>();
        for(String id : ids){
            items.add(this.getItemByStrId(sender, id));
        }

        return items;
    }

    @Override
    public ItemStack getItemByUUID(CommandSender sender, UUID id) {
        return this.getItemByStrId(sender, id.toString());
    }

    @Override
    public UUID addItem(CommandSender sender, ItemStack item) {
        if(item == null){
            sender.sendMessage("Try to add null object.");
            return null;
        }

        return this.customItemDB.addItem(item);
    }

    @Override
    public boolean removeItem(CommandSender sender, String id) {
        boolean state = this.customItemDB.removeItem(id);
        if(state){
            sender.sendMessage("Success to remove item.");
            return true;
        }

        sender.sendMessage("Unknown id.");
        return false;
    }

    @Override
    public boolean removeItem(CommandSender sender, UUID id) {
        return this.removeItem(sender, id.toString());
    }

    @Override
    public boolean removeItem(CommandSender sender, ItemStack item) {
        List<ItemStack> items = this.customItemDB.getItemsRange(0, -1);
        for(int i = 0; i < items.size(); i++){
            if(items.get(i) == item){
                return this.removeItem(sender, this.getCustomItemDB().getKeySet().get(i));
            }
        }

        sender.sendMessage("Unknown item.");
        return false;
    }

    @Override
    public List<ItemStack> getItemsRange(CommandSender sender, int startIndex, int length) {
        return this.customItemDB.getItemsRange(startIndex, length);
    }
}
