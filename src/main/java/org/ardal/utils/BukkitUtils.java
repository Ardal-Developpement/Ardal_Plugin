package org.ardal.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BukkitUtils {
    public static List<OfflinePlayer> getOfflinePlayersAsList(){
        List<OfflinePlayer> offlinePlayers = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            offlinePlayers.add(offlinePlayer);
        }

        return offlinePlayers;
    }

    public static List<String> getOfflinePlayerNamesAsList(){
        List<String> offlinePlayersNames = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            offlinePlayersNames.add(offlinePlayer.getName());
        }

        return offlinePlayersNames;
    }

    public static OfflinePlayer getOfflinePlayerFromName(String playerName){
        List<String> offlinePlayersNames = BukkitUtils.getOfflinePlayerNamesAsList();
        int playerIndex = offlinePlayersNames.indexOf(playerName);

        if(playerIndex == -1){
            return null;
        }

        return BukkitUtils.getOfflinePlayersAsList().get(playerIndex);
    }

    @Nullable
    public static Entity getEntityFromId(UUID id, UUID world){
        for(Entity entity : Bukkit.getWorld(world).getEntities()){
            if(entity.getUniqueId().equals(id)){
                return entity;
            }
        }

        return null;
    }

    public static void removeEnchant(ItemStack item){
        for(Enchantment e : item.getEnchantments().keySet())
        {
            item.removeEnchantment(e);
        }
    }
}
