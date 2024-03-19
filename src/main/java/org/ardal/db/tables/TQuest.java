package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.models.MQuest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class TQuest {
    @Nullable
    public int saveQuest(@NotNull MQuest mQuest) throws SQLException {
        PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                .prepareStatement("insert into quests(name, book_id, request_item_id, reward_item_id, is_active, is_delete) values (?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, mQuest.getName());
        statement.setInt(2, mQuest.getBookId());
        statement.setInt(3, mQuest.getRequestItemId());
        statement.setInt(4, mQuest.getRewardItemId());
        statement.setBoolean(5, mQuest.getIsActive());
        statement.setBoolean(6, mQuest.getIsDelete());

        statement.execute();
        int id = statement.getGeneratedKeys().getInt(1);
        statement.close();

        return id;
    }

    @Nullable
    public MQuest getQuestByName(@NotNull String name){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT book_id, request_item_id, reward_item_id, is_active, is_delete FROM quests WHERE name = ?"))
        {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int book_id = resultSet.getInt("book_id");
                    int request_item_id = resultSet.getInt("request_item_id");
                    int reward_item_id = resultSet.getInt("reward_item_id");
                    boolean is_active = resultSet.getBoolean("is_active");
                    boolean is_delete = resultSet.getBoolean("is_delete");

                    return new MQuest(name, book_id, request_item_id, reward_item_id, is_active, is_delete);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public Integer getQuestIdByName(@NotNull String name){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT id FROM quests WHERE name = ?"))
        {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public MQuest getQuestById(@NotNull int id){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name, book_id, request_item_id, reward_item_id, is_active, is_delete FROM quests WHERE id = ?"))
        {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int book_id = resultSet.getInt("book_id");
                    int request_item_id = resultSet.getInt("request_item_id");
                    int reward_item_id = resultSet.getInt("reward_item_id");
                    boolean is_active = resultSet.getBoolean("is_active");
                    boolean is_delete = resultSet.getBoolean("is_delete");

                    return new MQuest(name, book_id, request_item_id, reward_item_id, is_active, is_delete);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
