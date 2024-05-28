package org.ardal;


import net.citizensnpcs.api.CitizensAPI;
import org.ardal.api.managers.ArdalManager;
import org.ardal.db.Database;
import org.ardal.managers.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class Ardal extends JavaPlugin {
    private static Ardal instance;
    private Map<Class<? extends ArdalManager>, ArdalManager> ardalManagers = new HashMap<>();
    private Database db;

    @Override
    public void onEnable() {
        instance = this;

        this.db = new Database();
        db.initDb();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (CitizensAPI.hasImplementation() && CitizensAPI.getNPCRegistry() != null) {
                Bukkit.getScheduler().cancelTasks(this);

                this.registerManager(new MobManager());
                this.registerManager(new AdventureLevelManager());
                this.registerManager(new PlayerInfoManager());
                this.registerManager(new QuestManager());
                this.registerManager(new ItemManager());
                this.registerManager(new CustomInventoryManager());
                this.registerManager(new IndividualCmdManager());
                this.registerManager(new NPCManager());
                this.registerManager(new ChunkManager());

                enableManagers();
            }
        }, 5L, 20L);
    }

    @Override
    public void onDisable() {
        disableManagers();
        this.db.closeDataSource();
    }

    private void enableManagers(){
        this.ardalManagers.values().forEach(ArdalManager::onEnable);
    }

    private void disableManagers(){
        this.ardalManagers.values().forEach(ArdalManager::onDisable);
    }

    private void registerManager(ArdalManager manager){
        this.ardalManagers.put(manager.getClass(), manager);
    }


    @NotNull
    public <T extends ArdalManager> T getManager(Class<T> managerClass) {
        ArdalManager manager = ardalManagers.get(managerClass);
        if (managerClass.isInstance(manager)) {
            return managerClass.cast(manager);
        }

        throw new RuntimeException("Manager: " + managerClass.getName() + " is not registered.");
    }

    public static Ardal getInstance(){
        return instance;
    }

    public Database getDb() {
        return db;
    }

    public static void writeToLogger(String msg){
        Ardal.getInstance().getLogger().info(msg);
    }
}
