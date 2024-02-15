package org.ardal.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.managers.CustomNPCManager;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.UUID;

public abstract class CustomNPCObj {
    private final Location location;
    private UUID id;
    private String npcName;
    private Villager npc;
    private Boolean isVisible;

    public CustomNPCObj(String npcName, Location location){
        this.location = location;
        this.npcName = npcName;
        this.isVisible = true;

        this.npc = (Villager) Bukkit.getWorld("world").spawnEntity(location, EntityType.VILLAGER);
        this.id = this.npc.getUniqueId();
        this.setNpcProperties();
        this.registerNpc();
    }

    public CustomNPCObj(JsonObject npcObj, UUID npcId) {
        this.id = npcId;
        this.npcName = npcObj.get("npcName").getAsString();
        this.isVisible = npcObj.get("isVisible").getAsBoolean();
        this.location = LocationUtils.getLocationFromJson(npcObj.get("location").getAsJsonObject());

        this.npc = (Villager) BukkitUtils.getEntityFromId(this.id, this.location.getWorld().getUID());
        if(this.npc == null){
            this.npc = (Villager) Bukkit.getWorld("world").spawnEntity(location, EntityType.VILLAGER);
            this.id = this.npc.getUniqueId();
            this.setNpcProperties();

            CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
            JsonElement value = customNPCManager.getNpcDB().getDb().remove(npcId.toString());
            customNPCManager.getNpcDB().getDb().add(this.id.toString(), value);
            customNPCManager.getNpcDB().saveDB();
        }

        this.registerNpc();
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

    private void setNpcProperties(){
        npc.setCustomName(this.npcName);
        npc.setCustomNameVisible(true);
        npc.setInvulnerable(true);
        npc.setAdult();
        npc.setAI(false);
        npc.setCanPickupItems(false);
        npc.setSilent(true);


    }

    private void registerNpc(){
        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        customNPCManager.registerNpc(this);
    }

    public void setVisible(Boolean state){
        this.npc.setInvisible(!state);
        this.npc.setCustomNameVisible(state);
        this.isVisible = state;
    }

    public void destroy() {
        if (this.npc != null && !this.npc.isDead()) {
            this.npc.remove();
        }
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

    public UUID getId() {
        return id;
    }

    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }
}
