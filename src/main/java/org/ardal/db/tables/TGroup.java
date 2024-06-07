package org.ardal.db.tables;

import org.ardal.Ardal;

import java.sql.*;

public class TGroup {
    public int createGroup() {
        int id = -1;
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
                PreparedStatement statement = connection.prepareStatement("insert into `groups` (id) values (null)",
                        Statement.RETURN_GENERATED_KEYS))
        {
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to create a new group in the database.");
            e.printStackTrace();
        }

        return id;
    }

    public boolean deleteGroup(int groupId) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from `groups` WHERE id = ?"))
        {
            statement.setInt(1, groupId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete a group in the database.");
            e.printStackTrace();
        }

        return false;
    }
}
