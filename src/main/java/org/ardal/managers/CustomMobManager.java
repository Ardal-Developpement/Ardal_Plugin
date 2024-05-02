package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.managers.ArdalManager;
import org.ardal.entities.mobs.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CustomMobManager implements ArdalManager, Listener {
    private static final int MOB_AREA_CHECK_SPEED = 60; // in ticks

    private final HashMap<UUID, CustomMob> mobsRegister;

    private BukkitTask mobAreaCheckTask;

    public CustomMobManager() {
        mobsRegister = new HashMap<>();

        Ardal.getInstance().getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }

    @Override
    public void onEnable() {
        this.startMobAreaCheckTask();
    }

    @Override
    public void onDisable() {
        this.mobAreaCheckTask.cancel();
    }

    public void registerCustomMob(CustomMob mob) {
        mobsRegister.put(mob.getEntity().getUniqueId(), mob);
    }

    public void unregisterCustomMob(CustomMob mob) {
        mobsRegister.remove(mob.getEntity().getUniqueId());
    }

    @Nullable
    private CustomMob getMobInstanceByUuid(UUID mobUuid) {
        return this.mobsRegister.get(mobUuid);
    }


    /*
                        MOB AREA CHECK
     */

    private void startMobAreaCheckTask() {
        this.mobAreaCheckTask = Bukkit.getScheduler().runTaskTimer(Ardal.getInstance(), () -> {
            Set<Chunk> checkedChunks = new HashSet<>();

            for(CustomMob mob : mobsRegister.values()) {
                // TODO Optimize with hash map (to not make double check region request)
                // Check if we need to check if the mob as despawn (!chunk isLoad)
                if(mob.hasMoved()) {
                    if(checkedChunks.contains(mob.getEntity().getLocation().getChunk())) {
                        mob.destroy();
                    } else {
                        // check if chunk region can have a mob in it
                        if(!"magic".equals("magic")) {
                            checkedChunks.add(mob.getEntity().getLocation().getChunk());
                        }
                    }
                }
            }
        }, 0L, MOB_AREA_CHECK_SPEED);
    }


    /*
                        MOB DEATH
     */

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof CustomMob) {
            CustomMob mob = getMobInstanceByUuid(event.getEntity().getUniqueId());
            if(mob == null) {
                System.err.println("Failed to find register mob with UUID " + event.getEntity().getUniqueId());
                return;
            }

            System.out.println(event.getEntity().getKiller().getName() + ": kill " + mob.getEntity().getName());

            mob.giveXpToPlayer(event.getEntity().getKiller());
            mob.dropItemsReward(event.getEntity().getLocation());
        }
    }
}
