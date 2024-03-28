package org.ardal.db.tables;

import org.ardal.Ardal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.UUID;

public class TLocation {
    public int saveLocation(@NotNull Location location) {
        int id = -1;
        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into locations(world_uuid, x, y, z, yaw, pitch) values (?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, location.getWorld().getUID().toString());
            statement.setDouble(2, location.getX());
            statement.setDouble(3, location.getY());
            statement.setDouble(4, location.getZ());
            statement.setFloat(5, location.getYaw());
            statement.setFloat(6, location.getPitch());

            statement.executeUpdate();
            System.out.println("test1");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
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

    public boolean updateLocation(Location location){
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

            statement.setString(1, location.getWorld().getUID().toString());
            statement.setDouble(2, location.getX());
            statement.setDouble(3, location.getY());
            statement.setDouble(4, location.getZ());
            statement.setFloat(5, location.getYaw());
            statement.setFloat(6, location.getPitch());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update location in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public Location getLocationById(int id) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT world_uuid, x, y, z, yaw, pitch FROM locations WHERE id = ?"))
        {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String worldUuid = resultSet.getString("world_uuid");
                    World world = Bukkit.getWorld(UUID.fromString(worldUuid));
                    double x = resultSet.getDouble("x");
                    double y = resultSet.getDouble("y");
                    double z = resultSet.getDouble("z");
                    float yaw = resultSet.getFloat("yaw");
                    float pitch = resultSet.getFloat("pitch");

                    return new Location(world, x, y,z, yaw, pitch);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
