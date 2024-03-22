package org.ardal.objects;

import org.ardal.Ardal;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.api.npc.NpcInfo;
import org.ardal.models.npc.MNpc;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class NpcObj implements NpcInfo {
    private final MNpc mNpc;

    public NpcObj(MNpc mNpc){
        this.mNpc = mNpc;
    }

    @Override
    public String getUuid() {
        return this.mNpc.getUuid();
    }

    @Override
    public String getName() {
        return this.mNpc.getName();
    }

    @Override
    public boolean getIsVisible() {
        return this.mNpc.getIsVisible();
    }

    @Override
    public Location getLocation() {
        return Ardal.getInstance().getDb().gettLocation().getLocationById(this.mNpc.getLocationId());
    }

    @Override
    public CustomNpcType getNpcType() {
        return null;
    }

    @Override
    public boolean setName(@NotNull String newName) {
        return false;
    }

    @Override
    public boolean setIsVisible(boolean visibleState) {
        return false;
    }

    @Override
    public boolean setLocation(@NotNull Location newLocation) {
        return false;
    }

    @Override
    public boolean deleteNpc() {
        return false;
    }
}
