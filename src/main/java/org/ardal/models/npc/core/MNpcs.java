package org.ardal.models.npc.core;

import org.ardal.Ardal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MNpcs {
    private String uuid;
    private String name;
    private boolean is_visible;
    private int location_id;
    private String type;

    public MNpcs(String uuid, String name, boolean is_visible, int location_id, String type) {
        this.uuid = uuid;
        this.name = name;
        this.is_visible = is_visible;
        this.location_id = location_id;
        this.type = type;
    }

    @Nullable
    public void createNpc(@NotNull MNpcs mNpcs) throws SQLException {
        PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                .prepareStatement("insert into npc(uuid, name, is_visible, location_id, type) values (?,?,?,?,?)");

        statement.setString(1, mNpcs.uuid);
        statement.setString(2, mNpcs.name);
        statement.setBoolean(3, mNpcs.is_visible);
        statement.setInt(4, mNpcs.location_id);
        statement.setString(5, mNpcs.type);

        statement.execute();
        statement.close();
    }

    @Nullable
    public MNpcs findNpcByUuid(@NotNull String uuid){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name, is_visible, location_id, type FROM npc WHERE uuid = ?"))
        {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    boolean is_visible = resultSet.getBoolean("is_visible");
                    int location_id = resultSet.getInt("location_id");
                    String type = resultSet.getString("type");

                    return new MNpcs(uuid, name, is_visible, location_id, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
