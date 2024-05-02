package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.managers.ArdalManager;
import org.ardal.db.tables.TAdventureLevel;
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

        if(this.levels.isEmpty()) {
            this.createDefaultLevel();
            this.loadLevels();
        }
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

    private void createDefaultLevel() {
        System.out.println("Creating default adventure level.");

        TAdventureLevel tAdventureLevel = Ardal.getInstance().getDb().gettAdventureLevel();
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(0, "Paysan", 0));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(1, "Roturier", 100));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(2, "Apprenti", 1000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(3, "Écuyer", 3000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(4, "Mercenaire", 6000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(5, "Chevalier", 10000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(6, "Seigneur", 25000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(7, "Baron", 50000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(8, "Comte", 100000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(9, "Duc", 250000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(10, "Prince", 5000000));
        tAdventureLevel.createAdventureLevel(new MAdventureLevel(11, "Roi", 1000000));
    }
}
