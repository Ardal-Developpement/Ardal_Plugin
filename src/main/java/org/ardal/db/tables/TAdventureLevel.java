package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.models.MAdventureLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TAdventureLevel {
    @Nullable
    public boolean createAdventureLevel(@NotNull MAdventureLevel mAdventureLevel) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("insert into adventure_level(level, name, xp_required) values (?,?,?)"))
        {
            statement.setInt(1, mAdventureLevel.getLevel());
            statement.setString(2, mAdventureLevel.getName());
            statement.setInt(3, mAdventureLevel.getXpRequire());

            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save adventure level in database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateAdventureLevel(MAdventureLevel mAdventureLevel) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update adventure_level set " +
                             "name = ?, " +
                             "xp_required = ? " +
                             "where level = ?"))
        {
            statement.setString(1, mAdventureLevel.getName());
            statement.setInt(2, mAdventureLevel.getXpRequire());
            statement.setInt(3, mAdventureLevel.getLevel());


            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update quest npc info in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public MAdventureLevel getAdventureLevelByLevel(int level) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name, xp_required FROM adventure_level WHERE level = ?"))
        {
            statement.setInt(1, level);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MAdventureLevel(
                            level,
                            resultSet.getString("name"),
                            resultSet.getInt("xp_required"));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch adventure level by level in database.");
            e.printStackTrace();
        }
        return null;
    }

    public List<MAdventureLevel> getAllAdventureLevel() {
        List<MAdventureLevel> levels = new ArrayList<>();
        String request = "select level, name, xp_required from adventure_level";
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement(request))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    levels.add(new MAdventureLevel(
                            resultSet.getInt("level"),
                            resultSet.getString("name"),
                            resultSet.getInt("xp_required")
                    ));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch adventure levels in database.");
            e.printStackTrace();
        }

        return levels;
    }
}
