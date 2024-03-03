package org.ardal;


import org.ardal.api.managers.ArdalManager;
import org.ardal.managers.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class Ardal extends JavaPlugin {
    private static Ardal instance;
    private List<ArdalManager> ardalManagers;

    @Override
    public void onEnable() {
        instance = this;

        this.registerManager(new CustomNPCManager());
        this.registerManager(new PlayerInfoManager());
        this.registerManager(new QuestManager());
        this.registerManager(new CustomItemManager());
        this.registerManager(new CustomInventoryManager());
        this.registerManager(new ShortCutCmdManager());

        enableManagers();
    }

    @Override
    public void onDisable() {
        disableManagers();
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

    public static void writeToLogger(String msg){
        Ardal.getInstance().getLogger().info(msg);
    }
}
