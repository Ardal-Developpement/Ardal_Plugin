package org.ardal.entities.mobs;

import org.ardal.Ardal;
import org.ardal.api.entities.mobs.MobType;
import org.ardal.entities.mobs.type.CustomSkeleton;
import org.ardal.managers.MobManager;
import org.ardal.objects.PlayerObj;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CustomMob  {
    private static final int DEFAULT_DETECTED_MIN_MOVE_RANGE = 1;

    private final int xpReward;
    private final int detectedMinMoveRange;
    private final Location entityLocation;
    private final EntityType entityType;

    private Location oldLocation = null;
    private Entity entity;

    public CustomMob(EntityType entityType, Location entityLocation, int xpReward, int detectedMinMoveRange) {
        this.entityType = entityType;
        this.entityLocation = entityLocation;
        this.xpReward = xpReward;
        this.detectedMinMoveRange = detectedMinMoveRange;

        this.oldLocation = entityLocation;
    }

    public CustomMob(EntityType entityType, Location location, int xpReward) {
        this(entityType, location, xpReward, DEFAULT_DETECTED_MIN_MOVE_RANGE);
    }

    public abstract List<ItemStack> getItemsReward();
    public abstract void setEntityProperty();

    public void spawn() {
        this.entity = this.entityLocation.getWorld().spawnEntity(this.entityLocation, entityType);
        this.setEntityProperty();

        Ardal.getInstance().getManager(MobManager.class).registerCustomMob(this);
    }

    public void destroy() {
        this.entity.remove();
        Ardal.getInstance().getManager(MobManager.class).unregisterCustomMob(this);
    }

    public Entity getEntity() {
        return entity;
    }



    /*
                        MOB DEATH
     */

    public void giveXpToPlayer(Player player) {
        PlayerObj playerObj = new PlayerObj(player);
        playerObj.addAdventureXp(this.xpReward);
    }

    /*
                        MOB AREA CHECK
     */

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


    @Nullable
    public static CustomMob invokeCustomMob(MobType mobType, Location spawningLocation) {
        switch (mobType) {
            case SKELETON:
                return new CustomSkeleton(spawningLocation);
            default:
                return null;
        }
    }
}
