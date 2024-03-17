package org.ardal.models.npc.core;

public class MNpc {
    private String uuid;
    private String name;
    private boolean is_visible;
    private int location_id;
    private String type;

    public MNpc(String uuid, String name, boolean is_visible, int location_id, String type) {
        this.uuid = uuid;
        this.name = name;
        this.is_visible = is_visible;
        this.location_id = location_id;
        this.type = type;
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
