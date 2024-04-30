package org.ardal.db.tables.npc.type;

import org.ardal.Ardal;
import org.ardal.models.npc.type.MQuestNpcInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TQuestNpcInfo {
    @Nullable
    public boolean createQuestNpcInfo(@NotNull MQuestNpcInfo mQuestNpcInfo) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("insert into quest_npc_info(npc_uuid, nb_quest_show) values (?,?)"))
        {
            statement.setString(1, mQuestNpcInfo.getNpcUuid());
            statement.setInt(2, mQuestNpcInfo.getNbQuestShow());

            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save quest npc info in database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateQuestNpcInfo(MQuestNpcInfo mQuestNpcInfo) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update quest_npc_info set " +
                             "nb_quest_show = ? " +
                             "where npc_uuid = ?"))
        {
            statement.setString(1, mQuestNpcInfo.getNpcUuid());
            statement.setInt(2, mQuestNpcInfo.getNbQuestShow());

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update quest npc info in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public MQuestNpcInfo getNpcQuestInfoByUuid(String uuid) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT nb_quest_show FROM quest_npc_info WHERE npc_uuid = ?"))
        {
            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MQuestNpcInfo(uuid, resultSet.getInt("nb_quest_show"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
