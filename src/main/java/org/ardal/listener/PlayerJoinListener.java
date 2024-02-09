package org.ardal.listener;


import org.ardal.db.PlayerInfoDB;
import org.ardal.objects.PlayerInfoObj;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final PlayerInfoDB playerInfoDB;
    public PlayerJoinListener(PlayerInfoDB playerInfoDB){
        this.playerInfoDB = playerInfoDB;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(playerInfoDB.getDb().getAsJsonObject(player.getUniqueId().toString()) == null){
            playerInfoDB.addPlayer(new PlayerInfoObj(player));
        }
    }
}
