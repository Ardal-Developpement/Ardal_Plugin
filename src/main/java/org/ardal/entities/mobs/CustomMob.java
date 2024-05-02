package org.ardal.entities.mobs;

import org.ardal.Ardal;
import org.ardal.api.entities.mobs.MobType;
import org.ardal.managers.CustomMobManager;
import org.ardal.objects.PlayerObj;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public abstract class CustomMob implements Listener {
    private static final int DEFAULT_DETECTED_MIN_MOVE_RANGE = 1;

    private final LivingEntity entity;
    private final MobType mobType;
    private final int xpToEarn;
    private final int detectedMinMoveRange;


    public CustomMob(LivingEntity entityMobType, MobType mobType, int xpReward, int detectedMinMoveRange) {
        this.entity = entityMobType;
        this.mobType = mobType;
        this.xpToEarn = xpReward;
        this.detectedMinMoveRange = detectedMinMoveRange;

        Ardal.getInstance().getManager(CustomMobManager.class).registerCustomMob(this);
    }

    public CustomMob(LivingEntity entityMobType, MobType mobType, int xpReward) {
        this(entityMobType, mobType, xpReward, DEFAULT_DETECTED_MIN_MOVE_RANGE);
    }

    public abstract List<ItemStack> getItemsReward();
    public abstract UUID getMobUuid();

    public void destroy() {
        this.entity.remove();
        Ardal.getInstance().getManager(CustomMobManager.class).unregisterCustomMob(this);
    }

    public LivingEntity getEntity() {
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

    /**
     * Check if a mob move more than DISTANCE_MIN
     *
     * @return true if the mob move
     */
    private Location oldLocation = null;
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
