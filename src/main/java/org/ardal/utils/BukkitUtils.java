package org.ardal.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

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

    public static OfflinePlayer playerNameToOfflinePlayer(String playerName){
        List<String> offlinePlayersNames = BukkitUtils.getOfflinePlayerNamesAsList();
        int playerIndex = offlinePlayersNames.indexOf(playerName);

        if(playerIndex == -1){
            return null;
        }

        return BukkitUtils.getOfflinePlayersAsList().get(playerIndex);
    }
}
