package org.ardal.models.npc;

import org.ardal.Ardal;
import org.ardal.api.entities.npc.NpcType;

public class MNpc {
    private String uuid;
    private String name;
    private String skin_name;
    private boolean is_visible;
    private int location_id;
    private NpcType type;

    public MNpc(String uuid, String name, String skin_name, boolean is_visible, int location_id, NpcType type) {
        this.uuid = uuid;
        this.name = name;
        this.skin_name = skin_name;
        this.is_visible = is_visible;
        this.location_id = location_id;
        this.type = type;
    }

    public boolean updateNpc(){
        return Ardal.getInstance().getDb().gettNpc().updateNpc(this);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getSkinName() {
        return skin_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkinName(String skin_name) {
        this.skin_name = skin_name;
    }

    public boolean getIsVisible() {
        return is_visible;
    }

    public void setIsVisible(boolean is_visible) {
        this.is_visible = is_visible;
    }

    public int getLocationId() {
        return location_id;
    }

    public void setLocationId(int location_id) {
        this.location_id = location_id;
    }

    public NpcType getType() {
        return type;
    }

    public void setType(NpcType type) {
        this.type = type;
    }
}
