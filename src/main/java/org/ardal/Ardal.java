package org.ardal;


import net.citizensnpcs.api.CitizensAPI;
import org.ardal.api.managers.ArdalManager;
import org.ardal.db.Database;
import org.ardal.managers.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class Ardal extends JavaPlugin {
    private static Ardal instance;
    private List<ArdalManager> ardalManagers;
    private Database db;

    @Override
    public void onEnable() {
        instance = this;

        this.db = new Database();
        db.initDb();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (CitizensAPI.hasImplementation() && CitizensAPI.getNPCRegistry() != null) {
                Bukkit.getScheduler().cancelTasks(this);

                this.registerManager(new PlayerInfoManager());
                this.registerManager(new QuestManager());
                this.registerManager(new CustomItemManager());
                this.registerManager(new CustomInventoryManager());
                this.registerManager(new IndividualCmdManager());
                this.registerManager(new NPCManager());
                this.registerManager(new CustomMobManager());

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
        this.ardalManagers.forEach(ArdalManager::onEnable);
    }

    private void disableManagers(){
        this.ardalManagers.forEach(ArdalManager::onDisable);
    }

    private void registerManager(ArdalManager manager){
        if(this.ardalManagers == null) { this.ardalManagers = new ArrayList<>(); }

        if(!this.ardalManagers.contains(manager)){
            this.ardalManagers.add(manager);
        } else {
            this.getLogger().info("Manager already registered.");
        }
    }


    @NotNull
    public <T extends ArdalManager> T getManager(Class<T> managerClass) {
        for (ArdalManager manager : this.ardalManagers) {
            if (managerClass.isInstance(manager)) {
                return managerClass.cast(manager);
            }
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
