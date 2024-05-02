package org.ardal.entities.mobs;

import org.ardal.Ardal;
import org.ardal.managers.CustomMobManager;
import org.ardal.objects.PlayerObj;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public abstract class CustomMob implements Listener {
    private static final int DEFAULT_DETECTED_MIN_MOVE_RANGE = 1;

    private final int xpReward;
    private final int detectedMinMoveRange;

    private Entity entity;

    public CustomMob(int xpReward, int detectedMinMoveRange) {
        this.xpReward = xpReward;
        this.detectedMinMoveRange = detectedMinMoveRange;

        Ardal.getInstance().getManager(CustomMobManager.class).registerCustomMob(this);
    }

    public CustomMob(int xpReward) {
        this(xpReward, DEFAULT_DETECTED_MIN_MOVE_RANGE);
    }

    public abstract List<ItemStack> getItemsReward();
    public abstract UUID getMobUuid();
    public abstract void setEntityProperty();

    public void spawn(Location location, EntityType entityType) {
        this.entity = location.getWorld().spawnEntity(location, entityType);
        this.setEntityProperty();
    }

    public void destroy() {
        this.entity.remove();
        Ardal.getInstance().getManager(CustomMobManager.class).unregisterCustomMob(this);
    }

    public Entity getEntity() {
        return entity;
    }



    /*
                        MOB DEATH
     */

    public void giveXpToPlayer(Player player) {
        PlayerObj playerObj = new PlayerObj(player);
        //playerObj.addAdventureXp() // TODO
    }

    public void dropItemsReward(Location location) {
        for(ItemStack item : this.getItemsReward()) {
            location.getWorld().dropItem(location, item);
        }
    }



    /*
                        MOB AREA CHECK
     */

    private Location oldLocation = null;

    /**
     * Check if a mob move more than DISTANCE_MIN
     *
     * @return true if the mob move
     */
    public boolean hasMoved() {
        double movedRange = Math.abs(oldLocation.getX() - this.entity.getLocation().getX())
                            + Math.abs(oldLocation.getY() - this.entity.getLocation().getY())
                            + Math.abs(oldLocation.getZ() - this.entity.getLocation().getZ());

        if(movedRange > this.detectedMinMoveRange) {
            this.oldLocation = this.entity.getLocation();
            return true;
        }

        return false;
    }
}
