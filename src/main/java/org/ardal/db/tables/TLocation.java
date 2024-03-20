package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.models.MLocation;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TLocation {
    public int saveLocation(@NotNull MLocation mLocation) {
        int id = -1;
        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into locations(world_uuid, x, y, z, yaw, pitch) values (?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, mLocation.getWorldUuid());
            statement.setDouble(2, mLocation.getX());
            statement.setDouble(3, mLocation.getY());
            statement.setDouble(4, mLocation.getZ());
            statement.setDouble(5, mLocation.getYaw());
            statement.setDouble(6, mLocation.getPitch());

            statement.executeUpdate();
            id = statement.getGeneratedKeys().getInt("id");
            statement.close();
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save location in database.");
            e.printStackTrace();
        }

        return id;
    }

    public boolean deleteLocation(int locationId) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from locations WHERE id = ?"))
        {
            statement.setInt(1, locationId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete location in database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateLocation(MLocation mLocation){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update locations set " +
                             "world_uuid = ?," +
                             "x = ?," +
                             "y = ?," +
                             "z = ?," +
                             "yaw = ?," +
                             "pitch = ?" +
                             " where id = ?"))
        {

            statement.setDouble(1, mLocation.getX());
            statement.setDouble(2, mLocation.getY());
            statement.setDouble(3, mLocation.getZ());
            statement.setDouble(4, mLocation.getYaw());
            statement.setDouble(5, mLocation.getPitch());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update location in database.");
            e.printStackTrace();
        }

        return false;
    }
}
