package org.ardal.objects;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.citizensnpcs.trait.SkinTrait;
import net.citizensnpcs.util.NMS;
import org.ardal.Ardal;
import org.ardal.api.npc.NpcInfo;
import org.ardal.api.npc.NpcType;
import org.ardal.db.Database;
import org.ardal.db.tables.TLocation;
import org.ardal.managers.NPCManager;
import org.ardal.models.npc.MNpc;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class NpcObj implements NpcInfo {
    private MNpc mNpc;
    private NPC npc;

    public NpcObj(String npcName, Location location, NpcType npcType) {
        this.createCitizensNpc(npcName, location);

        Database db = Ardal.getInstance().getDb();
        int locationId = db.gettLocation().saveLocation(location);
        this.mNpc = new MNpc(this.npc.getUniqueId().toString(), npcName, true, locationId, npcType);
        this.setNpcProperties();
        db.gettNpc().createNpc(this.mNpc);
        Ardal.getInstance().getManager(NPCManager.class).registerNpc(this);
    }

    public NpcObj(String npcUuid) {
        this.mNpc = Ardal.getInstance().getDb().gettNpc().getNpcByUuid(npcUuid);

        UUID uuid = UUID.fromString(this.mNpc.getUuid());
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();

        for (NPC npc : npcRegistry) {
            if(npc.getUniqueId() == uuid) {
                this.npc = npc;
                break;
            }
        }

        Ardal.getInstance().getManager(NPCManager.class).registerNpc(this);
    }

    public abstract void onNPCInteract(PlayerInteractEntityEvent event);
    public abstract void onNpcManagentEvent(InventoryClickEvent event);

    private void createCitizensNpc(String npcName, Location location) {
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        this.npc.spawn(location);
        this.setNpcProperties();
        this.setNpcSkin(this.getName());
        Ardal.getInstance().getManager(NPCManager.class).registerNpc(this);
    }

    private void setNpcSkin(String skinName) {
        this.npc.getOrAddTrait(SkinTrait.class).setSkinName(skinName);
    }

    private void setNpcProperties(){
        this.npc.setName(this.mNpc.getName());

        this.npc.setProtected(true);
        this.npc.setUseMinecraftAI(false);
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
            this.npc.setName(newName);
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
            this.npc.teleport(newLocation, PlayerTeleportEvent.TeleportCause.COMMAND);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteNpc() {
        if(Ardal.getInstance().getDb().gettNpc().deleteNpc(this.getUuid())){
            this.npc.destroy();
            Ardal.getInstance().getDb().gettLocation().deleteLocation(this.mNpc.getLocationId());
            Ardal.getInstance().getManager(NPCManager.class).unregisterNpc(this);
            return true;
        }

        return false;
    }
}
