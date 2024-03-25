package org.ardal.objects;

import org.ardal.Ardal;
import org.ardal.api.npc.NpcInfo;
import org.ardal.api.npc.NpcType;
import org.ardal.db.Database;
import org.ardal.db.tables.TLocation;
import org.ardal.managers.NPCManager;
import org.ardal.models.npc.MNpc;
import org.ardal.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.EventListener;
import java.util.UUID;

public abstract class NpcObj implements NpcInfo, EventListener {
    private MNpc mNpc;
    private Villager npc;

    public NpcObj(String npcName, Location location, NpcType npcType) {
        this.npc = (Villager) Bukkit.getWorld("world").spawnEntity(location, EntityType.VILLAGER);
        this.setNpcProperties();

        Database db = Ardal.getInstance().getDb();
        int locationId = db.gettLocation().saveLocation(location);
        this.mNpc = new MNpc(this.npc.getUniqueId().toString(), npcName, true, locationId, npcType);
        db.gettNpc().createNpc(this.mNpc);
        Ardal.getInstance().getManager(NPCManager.class).registerNpc(this);
    }

    public NpcObj(String npcUuid) throws SQLException {
        this.mNpc = Ardal.getInstance().getDb().gettNpc().getNpcByUuid(npcUuid);
        if(this.mNpc == null) {
            throw new SQLException("Npc uuid does not exist in database.");
        }

        UUID uuid = UUID.fromString(this.mNpc.getUuid());
        this.npc = (Villager) BukkitUtils.getEntityFromId(uuid, this.getLocation().getWorld().getUID());

        if(this.npc == null) {
            // if npc entity has been killed
            this.npc = (Villager) Bukkit.getWorld("world").spawnEntity(this.getLocation(), EntityType.VILLAGER);
            this.setUuid(this.npc.getUniqueId().toString());
            this.setNpcProperties();
        }

        Ardal.getInstance().getManager(NPCManager.class).registerNpc(this);
    }

    public abstract void onNPCInteract(PlayerInteractEntityEvent event);
    public abstract void onNpcManagentEvent(InventoryClickEvent event);

    private void setNpcProperties(){
        npc.setCustomName(this.getName());
        npc.setCustomNameVisible(true);
        npc.setInvulnerable(true);
        npc.setAdult();
        npc.setAI(false);
        npc.setCanPickupItems(false);
        npc.setSilent(true);
    }

    @Override
    public String getUuid() {
        return this.mNpc.getUuid();
    }

    @Override
    public String getName() {
        return this.mNpc.getName();
    }

    @Override
    public boolean getIsVisible() {
        return this.mNpc.getIsVisible();
    }

    @Override
    public Location getLocation() {
        return Ardal.getInstance().getDb().gettLocation().getLocationById(this.mNpc.getLocationId());
    }

    @Override
    public boolean setUuid(@NotNull String newUuid) {
        this.mNpc.setUuid(newUuid);
        return this.mNpc.updateNpc();
    }

    @Override
    public NpcType getType() {
        return this.mNpc.getType();
    }


    @Override
    public boolean setName(@NotNull String newName) {
        this.mNpc.setName(newName);
        if(this.mNpc.updateNpc()) {
            this.npc.setCustomName(newName);
            return true;
        }

        return false;
    }

    @Override
    public boolean setIsVisible(boolean visibleState) {
        this.mNpc.setIsVisible(visibleState);
        if(this.mNpc.updateNpc()) {
            this.npc.setInvisible(!visibleState);
            this.npc.setCustomNameVisible(visibleState);
            return true;
        }

        return false;
    }

    @Override
    public boolean setLocation(@NotNull Location newLocation) {
        TLocation tLocation = Ardal.getInstance().getDb().gettLocation();
        int newLocationId = tLocation.saveLocation(newLocation);
        int oldLocationId = this.mNpc.getLocationId();

        this.mNpc.setLocationId(newLocationId);

        if(this.mNpc.updateNpc()) {
            tLocation.deleteLocation(oldLocationId);
            this.npc.teleport(newLocation); // TODO check teleport success
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteNpc() {
        if(Ardal.getInstance().getDb().gettNpc().deleteNpc(this.getUuid())){
            this.npc.remove();
            Ardal.getInstance().getDb().gettLocation().deleteLocation(this.mNpc.getLocationId());
            Ardal.getInstance().getManager(NPCManager.class).unregisterNpc(this);
            return true;
        }

        return false;
    }
}
