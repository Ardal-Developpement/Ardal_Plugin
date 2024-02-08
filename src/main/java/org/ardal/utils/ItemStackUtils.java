package org.ardal.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ItemStackUtils {
    public static void giveItemStackToPlayer(ItemStack itemStack, Player player){
        PlayerInventory playerInventory = player.getInventory();
        if(playerInventory.firstEmpty() != -1) {
            playerInventory.addItem(itemStack);
        } else {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        }
    }
    private static File getCustomItemFile(){
        File file = new File(Ardal.getInstance().getDataFolder(), "CustomItem.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return file;
    }

    public static JsonObject ItemStackToJson(ItemStack itemStack) {
        JsonObject itemObj = new JsonObject();
        UUID id = UUID.randomUUID();
        FileConfiguration config = YamlConfiguration.loadConfiguration(getCustomItemFile());

        config.set(id.toString(), itemStack);
        try {
            config.save(getCustomItemFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemObj.addProperty("name", itemStack.getItemMeta().getDisplayName());
        itemObj.addProperty("type", itemStack.getType().toString());
        itemObj.addProperty("id", id.toString());

        return itemObj;
    }

    public static ItemStack getItemStackFromJson(JsonObject jsonObject) {
        JsonElement idElem = jsonObject.get("id");
        if(idElem == null){
            return null;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(getCustomItemFile());
        return config.getItemStack(idElem.getAsString());
    }

    public static ItemStack getItemStackFromJson(JsonElement elem){
        return getItemStackFromJson(elem.getAsJsonObject());
    }
}
