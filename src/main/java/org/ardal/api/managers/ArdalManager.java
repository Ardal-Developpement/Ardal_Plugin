package org.ardal.api.managers;

public interface ArdalManager {
    /**
     * Call on plugin startup
     */
    void onEnable();

    /**
     * Call on plugin shutdown
     */
    void onDisable();
}

