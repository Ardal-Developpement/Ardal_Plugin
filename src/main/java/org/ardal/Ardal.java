package org.ardal;


import org.ardal.api.managers.ArdalManager;
import org.ardal.managers.CustomItemManager;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Ardal extends JavaPlugin {

    private static Ardal instance;
    private List<ArdalManager> ardalManagers;

    @Override
    public void onEnable() {
        instance = this;
        this.registerManager(new PlayerInfoManager());
        this.registerManager(new QuestManager());
        this.registerManager(new CustomItemManager());

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


    public <T extends ArdalManager> T getManager(Class<T> managerClass) {
        for (ArdalManager manager : this.ardalManagers) {
            if (managerClass.isInstance(manager)) {
                return managerClass.cast(manager);
            }
        }

        this.getLogger().severe("Manager: " + managerClass.getName() + " is not registered.");
        return null;
    }

    public static Ardal getInstance(){
        return instance;
    }

    public static void writeToLogger(String msg){
        Ardal.getInstance().getLogger().info(msg);
    }
}
