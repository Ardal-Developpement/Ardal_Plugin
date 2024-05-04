package org.ardal.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BukkitUtils {
    public static List<OfflinePlayer> getOfflinePlayers(){
        List<OfflinePlayer> offlinePlayers = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            offlinePlayers.add(offlinePlayer);
        }

        return offlinePlayers;
    }

    public static List<String> getOfflinePlayerNames(){
        List<String> offlinePlayersNames = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            offlinePlayersNames.add(offlinePlayer.getName());
        }

        return offlinePlayersNames;
    }

    @Nullable
    public static OfflinePlayer getOfflinePlayerFromName(String playerName){
        List<String> offlinePlayersNames = BukkitUtils.getOfflinePlayerNames();
        int playerIndex = offlinePlayersNames.indexOf(playerName);

        if(playerIndex == -1){
            return null;
        }

        return BukkitUtils.getOfflinePlayers().get(playerIndex);
    }

    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
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

    private static Map<ItemStack, Integer> getItemOccurrences(List<ItemStack> items){
        Map<ItemStack, Integer> map = new HashMap<>();

        for(ItemStack item : items){
            if(item == null || item.getType().equals(Material.AIR)) { continue; }

            ItemStack tmpItem = item.clone();
            tmpItem.setAmount(1);

            map.put(tmpItem, map.getOrDefault(tmpItem, 0) + item.getAmount());
        }

        return map;
    }

    /**
     * @return if l1 contain l2
     */
    public static boolean itemListContainItems(List<ItemStack> l1, List<ItemStack> l2){
        Map<ItemStack, Integer> m1 = getItemOccurrences(l1);
        Map<ItemStack, Integer> m2 = getItemOccurrences(l2);

        if (m1.size() < m2.size()) {
            return false;
        }

        for (Map.Entry<ItemStack, Integer> entry : m2.entrySet()) {
            ItemStack key = entry.getKey();
            Integer value = entry.getValue();
            if (!m1.containsKey(key) || m1.get(key) < value) {
                return false;
            }
        }

        return true;
    }
}
