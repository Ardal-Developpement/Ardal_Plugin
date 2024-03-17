package org.ardal.models;

import org.ardal.Ardal;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MPlayer {
    private String uuid;
    private String name;
    private int adventure_level;
    private Date quest_cooldown;
    private MPlayer(String uuid, String name, int adventure_level, @Nullable Date quest_cooldown) {
        this.uuid = uuid;
        this.name = name;
        this.adventure_level = adventure_level;
        this.quest_cooldown = quest_cooldown;
    }

    @Nullable
    public MPlayer createPlayer(@NotNull OfflinePlayer player) throws SQLException {
        MPlayer mPlayer = new MPlayer(player.getUniqueId().toString(), player.getName(), 0, null);

        PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                .prepareStatement("insert into player(uuid, name, adventure_level, quest_cooldown) values (?,?,?,?)");

        statement.setString(1, mPlayer.getUuid());
        statement.setString(2, mPlayer.getName());
        statement.setInt(3, mPlayer.getAdventure_level());
        statement.setTimestamp(4, new Timestamp(mPlayer.getQuest_cooldown().getTime()));

        return mPlayer;
    }

    @Nullable
    public MPlayer findPlayerByUUID(@NotNull String uuid) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name, adventure_level, quest_cooldown FROM player WHERE uuid = ?"))
        {

            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int adventureLevel = resultSet.getInt("adventure_level");
                    Timestamp questCooldownTimestamp = resultSet.getTimestamp("quest_cooldown");
                    Date questCooldownDate = new Date(questCooldownTimestamp.getTime());
                    return new MPlayer(uuid, name, adventureLevel, questCooldownDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getKeySet() {
        List<String> keySet = new ArrayList<>();

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT uuid FROM player"))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    keySet.add(resultSet.getString("uuid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keySet;
    }

    public List<String> getPlayerNames() {
        List<String> names = new ArrayList<>();

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name FROM player"))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    names.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
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

    public int getAdventure_level() {
        return adventure_level;
    }

    public void setAdventure_level(int adventure_level) {
        this.adventure_level = adventure_level;
    }

    public Date getQuest_cooldown() {
        return quest_cooldown;
    }

    public void setQuest_cooldown(Date quest_cooldown) {
        this.quest_cooldown = quest_cooldown;
    }
}
