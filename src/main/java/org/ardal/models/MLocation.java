package org.ardal.models;

import org.ardal.Ardal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MLocation {
    private int id;
    private String world_uuid;
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;

    public MLocation(int id, String world_uuid, double x, double y, double z, double yaw, double pitch) {
        this.id = id;
        this.world_uuid = world_uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Nullable
    public int createLocation(@NotNull MLocation mLocation) throws SQLException {
        PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                .prepareStatement("insert into location(world_uuid, x, y, z, yaw, pitch) values (?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, mLocation.world_uuid);
        statement.setDouble(2, x);
        statement.setDouble(3, y);
        statement.setDouble(4, z);
        statement.setDouble(5, yaw);
        statement.setDouble(6, pitch);

        statement.execute();
        int id = statement.getGeneratedKeys().getInt(1);
        statement.close();

        return id;
    }

    public MLocation findLocationById(int id) {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorld_uuid() {
        return world_uuid;
    }

    public void setWorld_uuid(String world_uuid) {
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
