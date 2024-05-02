package org.ardal.models;

public class MAdventureLevel {
    private int level;
    private String name;
    private int xp_require;

    public MAdventureLevel(int level, String name, int xp_require) {
        this.level = level;
        this.name = name;
        this.xp_require = xp_require;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXpRequire() {
        return xp_require;
    }

    public void setXpRequire(int xp_require) {
        this.xp_require = xp_require;
    }
}
