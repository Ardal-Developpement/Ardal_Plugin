package org.ardal.db.tables.npc;

import org.ardal.Ardal;
import org.ardal.api.npc.NpcType;
import org.ardal.models.npc.MNpc;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TNpc {

    @Nullable
    public String createNpc(MNpc mNpc) {
        String npcUuid = null;
        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into npcs(uuid, name, is_visible, location_id, type) values (?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, mNpc.getUuid());
            statement.setString(2, mNpc.getName());
            statement.setBoolean(3, mNpc.getIsVisible());
            statement.setInt(4, mNpc.getLocationId());
            statement.setString(5, mNpc.getType().toString());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                npcUuid = generatedKeys.getString(1);
            }

            statement.close();
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save npc in database.");
            e.printStackTrace();
        }

        return npcUuid;
    }

    @Nullable
    public MNpc getNpcByUuid(String uuid) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name, is_visible, location_id, type FROM npcs WHERE uuid = ?"))
        {
            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MNpc(uuid,
                            resultSet.getString("name"),
                            resultSet.getBoolean("is_visible"),
                            resultSet.getInt("location_id"),
                            NpcType.getNpcTypeByName(resultSet.getString("type")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateNpc(MNpc mNpc) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update npcs set " +
                             "name = ?," +
                             "is_visible = ?," +
                             "location_id = ?," +
                             "type = ?" +
                             " WHERE uuid = ?"))
        {
            statement.setString(1, mNpc.getName());
            statement.setBoolean(2, mNpc.getIsVisible());
            statement.setInt(3, mNpc.getLocationId());
            statement.setString(4, mNpc.getType().toString());
            statement.setString(5, mNpc.getUuid());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update npc in database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteNpc(String uuid) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from npcs WHERE uuid = ?"))
        {
            statement.setString(1, uuid);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete npc in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public String getNpcName(String uuid) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name FROM npcs WHERE uuid = ?"))
        {

            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch npc name in database.");
            e.printStackTrace();
        }
        return null;
    }

    public boolean npcExist(String uuid) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name FROM npcs WHERE uuid = ?"))
        {

            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch npc in database.");
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllNpcUuids() {
        List<String> npcNames = new ArrayList<>();

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT uuid FROM npcs");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                npcNames.add(resultSet.getString("uuid"));
            }

        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch npc uuids in database.");
            e.printStackTrace();
        }
        return npcNames;
    }
}
