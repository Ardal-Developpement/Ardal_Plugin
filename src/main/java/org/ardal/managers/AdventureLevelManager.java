package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.managers.ArdalManager;
import org.ardal.models.MAdventureLevel;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class AdventureLevelManager implements ArdalManager {
    private HashMap<Integer, MAdventureLevel> levels;
    private HashMap<Integer, MAdventureLevel> nextLevels;

    public AdventureLevelManager() {

    }

    @Override
    public void onEnable() {
        this.loadLevels();
    }

    @Override
    public void onDisable() {

    }

    /**
     * Load in cache adventure level
     */
    private void loadLevels() {
        this.levels = new HashMap<>();
        this.nextLevels = new HashMap<>();
        Integer previousLevel = null;

        for(MAdventureLevel level : Ardal.getInstance().getDb().gettAdventureLevel().getAllAdventureLevel()) {
            this.levels.put(level.getLevel(), level);
            this.nextLevels.put(previousLevel, level);
            previousLevel = level.getLevel();
        }

        this.nextLevels.put(previousLevel, null);
    }

    public MAdventureLevel getAdventureLevelByLevel(int level) {
        return this.levels.get(level);
    }

    @Nullable
    public MAdventureLevel getNextAdventureLevel(int level) {
        return this.nextLevels.get(level);
    }
}
