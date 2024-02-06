package org.ardal.npc;

import com.google.gson.JsonObject;
import org.ardal.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Objects;
import java.util.UUID;

public class CustomNPC implements Listener {
    private final UUID npcUUID;
    private final String npcName;
    private final Location location;
    private Villager npc;
    private Boolean isVisible;

    public CustomNPC(String npcName, Location location){
        this.npcUUID = UUID.randomUUID();
        this.location = location;
        this.npcName = npcName;
        this.isVisible = true;
    }

    public CustomNPC(JsonObject npcObj){
        this.npcName = npcObj.getAsJsonObject("npcName").getAsString();
        this.npcUUID = UUID.fromString(npcObj.getAsJsonObject("npcUUID").getAsString());
        this.isVisible = npcObj.getAsJsonObject("npcName").getAsBoolean();
        this.location = LocationUtils.getLocationFromJson(npcObj.getAsJsonObject("location"));
    }

    @EventHandler
    public void onNPCInteract(PlayerInteractEntityEvent event) {
        if(!isVisible) { return; }

        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            Villager npc = (Villager) event.getRightClicked();
            if (Objects.equals(npc.getCustomName(), this.npcName)) {
                Player player = event.getPlayer();

                //Open inventory
            }
        }
    }

    public void invoke(){
        this.npc = (Villager) Bukkit.getWorld("world").spawnEntity(location, EntityType.VILLAGER);
        npc.setCustomName(this.npcName);
        npc.setCustomNameVisible(true);
        npc.setInvulnerable(true);
        npc.setAdult();
        npc.setAI(false);
        npc.setCanPickupItems(false);
        npc.setSilent(true);
    }

    public void destroy(){
        if(this.npc != null && !this.npc.isDead()){
            this.npc.remove();
        }
    }

    public void setVisible(Boolean state){
        this.npc.setInvisible(!state);
        this.npc.setCustomNameVisible(state);
        this.isVisible = state;
    }

    public JsonObject toJson(){
        JsonObject ncpObj = new JsonObject();
        ncpObj.addProperty("npcUUID", this.npcUUID.toString());
        ncpObj.addProperty("npcName", this.npcName);
        ncpObj.addProperty("isVisible", this.isVisible);
        ncpObj.add("location", LocationUtils.locationToJson(this.location));

        return ncpObj;
    }

    public String getNpcName() {
        return npcName;
    }

    public Location getLocation() {
        return location;
    }

    public UUID getNpcUUID() {
        return npcUUID;
    }
}
