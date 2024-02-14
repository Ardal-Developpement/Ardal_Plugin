package org.ardal.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public abstract class CustomNPCObj {
    private final String npcName;
    private final Location location;
    private Villager npc;
    private Boolean isVisible;

    public CustomNPCObj(String npcName, Location location){
        this.location = location;
        this.npcName = npcName;
        this.isVisible = true;
    }

    public CustomNPCObj(JsonObject npcObj){
        this.npcName = npcObj.getAsJsonObject("npcName").getAsString();
        this.isVisible = npcObj.getAsJsonObject("isVisible").getAsBoolean();
        this.location = LocationUtils.getLocationFromJson(npcObj.getAsJsonObject("location"));
    }

    public JsonObject toJson(){
        JsonObject npcObj = new JsonObject();
        npcObj.addProperty("npcName", this.npcName);
        npcObj.addProperty("isVisible", this.isVisible);
        npcObj.add("location", LocationUtils.locationToJson(this.location));
        npcObj.add("additionalProperties", this.additionalProperties());

        return npcObj;
    }

    public abstract JsonElement additionalProperties();
    public abstract CustomNpcType getNpcType();
    public abstract void onNPCInteract(PlayerInteractEntityEvent event);

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

    public String getNpcName() {
        return npcName;
    }

    public Location getLocation() {
        return location;
    }

    public Villager getNpcEntity() {
        return npc;
    }
}
