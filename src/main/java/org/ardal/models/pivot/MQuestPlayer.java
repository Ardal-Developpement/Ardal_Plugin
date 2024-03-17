package org.ardal.models.pivot;

import org.ardal.Ardal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MQuestPlayer {
    private int quest_id;
    private String player_uuid;
    private boolean is_finished;
    private Date start_date;

    public MQuestPlayer(int quest_id, String player_uuid, boolean is_finished, Date start_date) {
        this.quest_id = quest_id;
        this.player_uuid = player_uuid;
        this.is_finished = is_finished;
        this.start_date = start_date;
    }

    public void createQuestPlayer(MQuestPlayer mQuestPlayer) throws SQLException {
        PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                .prepareStatement("insert into quest_player(quest_id, player_uuid, is_finished, start_date) values (?,?,?,?)");

        statement.setInt(1, mQuestPlayer.quest_id);
        statement.setString(2, mQuestPlayer.player_uuid);
        statement.setBoolean(3, mQuestPlayer.is_finished);
        statement.setTimestamp(4, new Timestamp(mQuestPlayer.start_date.getTime()));

        statement.execute();
        statement.close();
    }

    public List<MQuestPlayer> findQuestPlayerByQuestId(int questId) {
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
                            new Date(resultSet.getTimestamp("start_date").getTime())
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mQuestPlayerList;
    }

    public List<MQuestPlayer> findQuestPlayerByPlayerUuid(String playerUuid) {
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
            e.printStackTrace();
        }
        return mQuestPlayerList;
    }

    public int getQuest_id() {
        return quest_id;
    }

    public void setQuest_id(int quest_id) {
        this.quest_id = quest_id;
    }

    public String getPlayer_uuid() {
        return player_uuid;
    }

    public void setPlayer_uuid(String player_uuid) {
        this.player_uuid = player_uuid;
    }

    public boolean isIs_finished() {
        return is_finished;
    }

    public void setIs_finished(boolean is_finished) {
        this.is_finished = is_finished;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
}
