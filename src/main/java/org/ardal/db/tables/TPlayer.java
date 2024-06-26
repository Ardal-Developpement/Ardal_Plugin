package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.models.MPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TPlayer {
    public boolean createPlayer(@NotNull MPlayer mPlayer) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement("insert into players(uuid, name, adventure_level, adventure_xp, quest_cooldown) values (?,?,?,?,?)"))
        {
            statement.setString(1, mPlayer.getUuid());
            statement.setString(2, mPlayer.getName());
            statement.setInt(3, mPlayer.getAdventureLevel());
            statement.setInt(4, mPlayer.getAdventureXp());

            if(mPlayer.getQuestCooldown() == null) {
                statement.setNull(5, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(6, new Timestamp(mPlayer.getQuestCooldown().getTime()));
            }

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save player in database.");
        }

        return true;
    }


    @Nullable
    public MPlayer getPlayerByUUID(@NotNull String uuid) {
        MPlayer mPlayer = null;
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name, adventure_level, adventure_xp, quest_cooldown FROM players WHERE uuid = ?"))
        {
            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int adventureLevel = resultSet.getInt("adventure_level");
                    int adventureXp = resultSet.getInt("adventure_xp");
                    Timestamp questCooldownTimestamp = resultSet.getTimestamp("quest_cooldown");

                    java.util.Date questCooldownDate;
                    if(questCooldownTimestamp == null) {
                        questCooldownDate = null;
                    } else {
                        questCooldownDate = new Date(questCooldownTimestamp.getTime());
                    }

                    mPlayer = new MPlayer(uuid, name, adventureLevel, adventureXp, questCooldownDate);
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to get player by uuid in the database.");
            e.printStackTrace();
        }

        return mPlayer;
    }

    @Nullable
    public boolean isPlayerExistByUuid(@NotNull String uuid) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT 1 FROM players WHERE uuid = ?"))
        {
            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to check if a player exist by uuid in the database.");
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getPlayerUuids() {
        List<String> playerUuidList = new ArrayList<>();

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT uuid FROM players"))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    playerUuidList.add(resultSet.getString("uuid"));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch player uuids in database.");
            e.printStackTrace();
        }
        return playerUuidList;
    }

    public List<String> getPlayerNames() {
        List<String> names = new ArrayList<>();

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name FROM players"))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    names.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch player names in database.");
            e.printStackTrace();
        }
        return names;
    }

    public boolean setAdventureLevel(String uuid, int adventureLevel){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update players set adventure_level = ? WHERE uuid = ?"))
        {
            statement.setInt(1, adventureLevel);
            statement.setString(2, uuid);
           return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean setQuestCooldown(String uuid, Timestamp cooldown){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update players set quest_cooldown = ? WHERE uuid = ?"))
        {
            if(cooldown == null){
                statement.setNull(1, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(1, cooldown);
            }

            statement.setString(2, uuid);
           return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updatePlayer(MPlayer mPlayer){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update players set " +
                             "name = ?," +
                             "adventure_level = ?," +
                             "adventure_xp = ?," +
                             "quest_cooldown = ?" +
                             " where uuid = ?"))
        {
            statement.setString(1, mPlayer.getName());
            statement.setInt(2, mPlayer.getAdventureLevel());
            statement.setInt(3, mPlayer.getAdventureXp());

            if(mPlayer.getQuestCooldown() == null) {
                statement.setNull(4, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(4, new Timestamp(mPlayer.getQuestCooldown().getTime()));
            }

            statement.setString(5, mPlayer.getUuid());

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update player in database.");
            e.printStackTrace();
        }

        return false;
    }
}
