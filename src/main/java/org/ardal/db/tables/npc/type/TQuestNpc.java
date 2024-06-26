package org.ardal.db.tables.npc.type;

import org.ardal.Ardal;
import org.ardal.models.npc.type.MQuestNpc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TQuestNpc {
    @Nullable
    public boolean createQuestNpc(@NotNull MQuestNpc mQuestNpc) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                 .prepareStatement("insert into quest_npc(npc_uuid, quest_id, quest_coef, is_show) values (?,?,?,?)"))
        {
            statement.setString(1, mQuestNpc.getNpcUuid());
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

    public boolean updateQuestNpc(MQuestNpc mQuestNpc) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update quest_npc set " +
                             "quest_coef = ?," +
                             "is_show = ? " +
                             "where quest_id = ?"))
        {
            statement.setInt(1, mQuestNpc.getQuestCoef());
            statement.setBoolean(2, mQuestNpc.getIsShow());
            statement.setInt(3, mQuestNpc.getQuestId());

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update quest npc in database.");
            e.printStackTrace();
        }

        return false;
    }

    public List<MQuestNpc> getAllQuestNpcsByUuid(@NotNull String npc_uuid) {
        List<MQuestNpc> mQuestNpcList = new ArrayList<>();
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT quest_id, quest_coef, is_show FROM quest_npc WHERE npc_uuid = ?"))
        {

            statement.setString(1, npc_uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    int quest_id = resultSet.getInt("quest_id");
                    int quest_coef = resultSet.getInt("quest_coef");
                    boolean is_show = resultSet.getBoolean("is_show");

                    mQuestNpcList.add(new MQuestNpc(npc_uuid, quest_id, quest_coef, is_show));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch npc quest list in database.");
            e.printStackTrace();
        }

        return mQuestNpcList;
    }
}
