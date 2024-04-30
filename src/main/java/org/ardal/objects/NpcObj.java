package org.ardal.objects;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.CurrentLocation;
import net.citizensnpcs.trait.SkinTrait;
import org.ardal.Ardal;
import org.ardal.api.npc.NpcInfo;
import org.ardal.api.npc.NpcType;
import org.ardal.db.Database;
import org.ardal.db.tables.TLocation;
import org.ardal.exceptions.NpcNotFound;
import org.ardal.managers.NPCManager;
import org.ardal.models.npc.MNpc;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NpcObj implements NpcInfo {
    private final MNpc mNpc;
    private NPC npc;

    public NpcObj(String npcName, Location location, NpcType npcType) {
        this.createCitizensNpc(npcName, location);

        Database db = Ardal.getInstance().getDb();
        int locationId = db.gettLocation().saveLocation(location);
        this.mNpc = new MNpc(this.npc.getUniqueId().toString(), npcName, npcName, true, locationId, npcType);

        this.setNpcProperties();
        db.gettNpc().createNpc(this.mNpc);

        Ardal.getInstance().getManager(NPCManager.class).registerNpc(this);
    }

    public NpcObj(String npcUuid) throws NpcNotFound {
        this.mNpc = Ardal.getInstance().getDb().gettNpc().getNpcByUuid(npcUuid);

        UUID uuid = UUID.fromString(this.mNpc.getUuid());

        this.npc = CitizensAPI.getNPCRegistry().getByUniqueId(uuid);

        if(this.npc == null) {
            throw new NpcNotFound();
        }

        this.setNpcSkin(this.getSkinName(), false);
        Ardal.getInstance().getManager(NPCManager.class).registerNpc(this);
    }

    public void onNPCInteractEvent(NPCRightClickEvent event) {}
    public void onNpManagementEvent(InventoryClickEvent event) {}

    private void createCitizensNpc(String npcName, Location location) {
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        this.npc.spawn(location);
        this.setNpcSkin(npcName, false);
        Ardal.getInstance().getManager(NPCManager.class).registerNpc(this);
    }


    @Override
    public void setNpcSkin(@NotNull String skinName, boolean updateDB) {
        this.npc.getOrAddTrait(SkinTrait.class).setSkinName(skinName, true);

        if(updateDB) {
            this.mNpc.setSkinName(skinName);
            this.mNpc.updateNpc();
        }
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
    public String getSkinName() {
        return this.mNpc.getSkinName();
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
    public boolean setLocation(@NotNull Player player) {
        TLocation tLocation = Ardal.getInstance().getDb().gettLocation();
        int newLocationId = tLocation.saveLocation(player.getLocation());
        int oldLocationId = this.mNpc.getLocationId();

        this.mNpc.setLocationId(newLocationId);

        if(this.mNpc.updateNpc()) {

            tLocation.deleteLocation(oldLocationId);

            // Cannot find a better solution to rotate body and eyes correctly..
            this.npc.despawn();
            this.npc.spawn(player.getLocation());
            this.npc.teleport(player.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
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

    public static NpcObj getNpc(String npcUuid) throws NpcNotFound {
        NPCManager npcManager = Ardal.getInstance().getManager(NPCManager.class);
        NpcObj npc = npcManager.getRegisteredNpcByUuid(npcUuid);

        if(npc != null) {
            return npc;
        }

        return new NpcObj(npcUuid);
    }
}
