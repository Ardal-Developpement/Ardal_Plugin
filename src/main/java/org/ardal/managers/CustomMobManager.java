package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.managers.ArdalManager;
import org.ardal.entities.mobs.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomMobManager implements ArdalManager {
    private static final int MOB_AREA_CHECK_SPEED = 60; // in ticks

    private final List<CustomMob> mobsRegister;

    private BukkitTask mobAreaCheckTask;

    public CustomMobManager() {
        mobsRegister = new ArrayList<>();
    }

    @Override
    public void onEnable() {
        this.startMobAreaCheckTask();
    }

    @Override
    public void onDisable() {
        this.mobAreaCheckTask.cancel();
    }

    private void startMobAreaCheckTask() {
        this.mobAreaCheckTask = Bukkit.getScheduler().runTaskTimer(Ardal.getInstance(), () -> {
            Set<Chunk> checkedChunks = new HashSet<>();

            for(CustomMob mob : mobsRegister) {
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

    public void registerCustomMob(CustomMob mob) {
        if(!mobsRegister.contains(mob)) {
            mobsRegister.add(mob);
        }
    }

    public void unregisterCustomMob(CustomMob mob) {
        mobsRegister.remove(mob);
    }
}
