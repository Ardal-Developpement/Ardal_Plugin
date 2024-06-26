package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.models.pivot.MQuestPlayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TQuestPlayer {
    public boolean saveQuestPlayer(MQuestPlayer mQuestPlayer) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
                 PreparedStatement statement = connection
                     .prepareStatement("insert into quest_player(quest_id, player_uuid, is_finished, start_date) values (?,?,?,?)"))
        {
            statement.setInt(1, mQuestPlayer.getQuestId());
            statement.setString(2, mQuestPlayer.getPlayerUuid());
            statement.setBoolean(3, mQuestPlayer.getIsFinished());
            statement.setTimestamp(4, new Timestamp(mQuestPlayer.getStartDate().getTime()));

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e){
            Ardal.writeToLogger("Failed to save quest_player.");
            e.printStackTrace();
        }

        return false;
    }

    public List<MQuestPlayer> getQuestPlayerByQuestId(int questId) {
        List<MQuestPlayer> mQuestPlayerList = new ArrayList<>();
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT player_uuid, is_finished, start_date FROM quest_player WHERE quest_id = ?"))
        {
            statement.setInt(1, questId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    mQuestPlayerList.add(new MQuestPlayer(
                            questId,
                            resultSet.getString("player_uuid"),
                            resultSet.getBoolean("is_finished"),
                            new java.util.Date(resultSet.getTimestamp("start_date").getTime())
                    ));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to get quest player by quest id in the database.");
            e.printStackTrace();
        }
        return mQuestPlayerList;
    }

    public List<MQuestPlayer> getQuestPlayerByPlayerUuid(String playerUuid) {
        List<MQuestPlayer> mQuestPlayerList = new ArrayList<>();
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT quest_id, is_finished, start_date FROM quest_player WHERE player_uuid = ?"))
        {
            statement.setString(1, playerUuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    mQuestPlayerList.add(new MQuestPlayer(
                            resultSet.getInt("quest_id"),
                            playerUuid,
                            resultSet.getBoolean("is_finished"),
                            new Date(resultSet.getTimestamp("start_date").getTime())
                    ));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to get quest player by player uuid in the database.");
            e.printStackTrace();
        }
        return mQuestPlayerList;
    }

    public List<Integer> getQuestIdsByPlayerUuid(String playerUuid, boolean isFinished) {
        List<Integer> questIds = new ArrayList<>();
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT quest_id FROM quest_player WHERE player_uuid = ? AND is_finished = ?"))
        {
            statement.setString(1, playerUuid);
            statement.setBoolean(2, isFinished);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    questIds.add(resultSet.getInt("quest_id"));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to get quest ids by player uuid in the database.");
            e.printStackTrace();
        }
        return questIds;
    }

    public boolean setIsFinishedQuestPlayer(int questId, String playerUuid, boolean isFinished) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update quest_player set is_finished = ? WHERE quest_id = ? AND player_uuid = ?"))
        {
            statement.setBoolean(1, isFinished);
            statement.setInt(2, questId);
            statement.setString(3, playerUuid);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to set finish state of quest player in the database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeQuestPlayer(int questId, String playerUuid) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from quest_player WHERE quest_id = ? AND player_uuid = ?"))
        {
            statement.setInt(1, questId);
            statement.setString(2, playerUuid);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to remove quest player in the database.");
            e.printStackTrace();
        }

        return false;
    }
}
