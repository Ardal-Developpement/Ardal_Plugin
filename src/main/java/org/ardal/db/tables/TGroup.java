package org.ardal.db.tables;

import org.ardal.Ardal;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TGroup {
    @Nullable
    public int createGroup() {
        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into `groups` (id) values (null)",
                            Statement.RETURN_GENERATED_KEYS);

            statement.executeUpdate();
            int id = statement.getGeneratedKeys().getInt(1);
            statement.close();

            return id;

        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to create a new group in the database.");
            e.printStackTrace();
        }

        return -1;
    }

    public boolean deleteGroup(int groupId) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from `groups` WHERE id = ?"))
        {
            statement.setInt(1, groupId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
