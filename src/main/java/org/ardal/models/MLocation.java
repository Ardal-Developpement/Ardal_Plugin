package org.ardal.models;

import java.util.UUID;

public class MLocation {
    private int id;
    private UUID world_uuid;
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;

    public MLocation(int id, UUID world_uuid, double x, double y, double z, double yaw, double pitch) {
        this.id = id;
        this.world_uuid = world_uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getWorld_uuid() {
        return world_uuid;
    }

    public void setWorld_uuid(UUID world_uuid) {
        this.world_uuid = world_uuid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }
}
