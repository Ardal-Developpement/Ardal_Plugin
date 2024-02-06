package org.ardal;


import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ardal extends JavaPlugin {
    private PlayerInfoManager playerInfoManager;
    private QuestManager questManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.playerInfoManager = new PlayerInfoManager(this);
        this.questManager = new QuestManager(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void writeToLogger(String msg){
        Ardal plugin = Ardal.getPlugin(Ardal.class);
        plugin.getLogger().info(msg);
    }

    public PlayerInfoManager getPlayerInfoManager() {
        return playerInfoManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}
