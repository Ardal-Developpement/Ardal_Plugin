package org.ardal.db.tables.npc.type;

import org.ardal.Ardal;
import org.ardal.models.npc.type.MQuestNpc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class TQuestNpc {
    @Nullable
    public boolean createQuestNpc(@NotNull MQuestNpc mQuestNpc) throws SQLException {
        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into quest_npc(uuid, quest_id, quest_coef, is_show) values (?,?,?,?)");

            statement.setString(1, mQuestNpc.getUuid());
            statement.setInt(2, mQuestNpc.getQuestId());
            statement.setInt(3, mQuestNpc.getQuestCoef());
            statement.setBoolean(4, mQuestNpc.getIsShow());

            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save quest npc in database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateNpc(MQuestNpc mQuestNpc) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update quest_npc set " +
                             "quest_id = ?," +
                             "quest_coef = ?," +
                             "is_show = ?" +
                             "where uuid = ?"))
        {
            statement.setInt(1, mQuestNpc.getQuestId());
            statement.setInt(2, mQuestNpc.getQuestCoef());
            statement.setBoolean(3, mQuestNpc.getIsShow());
            statement.setString(4, mQuestNpc.getUuid());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update npc in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public MQuestNpc getQuestNpcByUuid(@NotNull String uuid){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT uuid, quest_id, quest_coef, is_show FROM quest_npc WHERE uuid = ?"))
        {

            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int quest_id = resultSet.getInt("quest_id");
                    int quest_coef = resultSet.getInt("quest_coef");
                    boolean is_show = resultSet.getBoolean("is_show");

                    return new MQuestNpc(uuid, quest_id, quest_coef, is_show);
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch npc quest in database.");
            e.printStackTrace();
        }
        return null;
    }
}
