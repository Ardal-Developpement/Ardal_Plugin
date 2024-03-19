package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.models.MQuests;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TQuests {
    @Nullable
    public int saveQuest(@NotNull MQuests mQuests) {
        int id = -1;
        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into quests(name, book_id, request_item_group_id, reward_item_group_id, is_active, is_delete) values (?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, mQuests.getName());
            statement.setString(2, mQuests.getBookId());
            statement.setInt(3, mQuests.getRequestItemId());
            statement.setInt(4, mQuests.getRewardItemId());
            statement.setBoolean(5, mQuests.getIsActive());
            statement.setBoolean(6, mQuests.getIsDelete());

            statement.executeUpdate();
            id = statement.getGeneratedKeys().getInt(1);
            statement.close();
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save quest in database.");
            e.printStackTrace();
        }

        return id;
    }

    public boolean deleteQuestByName(String questName) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from quests WHERE name = ?"))
        {
            statement.setString(1, questName);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public MQuests getQuestByName(@NotNull String name) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT book_id, synopsis, request_item_group_id, reward_item_group_id, is_active, is_delete FROM quests WHERE name = ?"))
        {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String book_id = resultSet.getString("book_id");
                    String synopsis = resultSet.getString("synopsis");
                    int request_item_id = resultSet.getInt("request_item_id");
                    int reward_item_id = resultSet.getInt("reward_item_id");
                    boolean is_active = resultSet.getBoolean("is_active");
                    boolean is_delete = resultSet.getBoolean("is_delete");

                    return new MQuests(name, book_id, synopsis, request_item_id, reward_item_id, is_active, is_delete);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public Integer getQuestIdByName(@NotNull String name) {
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
    public MQuests getQuestById(@NotNull int id) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name, book_id, request_item_group_id, reward_item_group_id, is_active, is_delete FROM quests WHERE id = ?"))
        {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String book_id = resultSet.getString("book_id");
                    String synopsis = resultSet.getString("synopsis");
                    int request_item_id = resultSet.getInt("request_item_id");
                    int reward_item_id = resultSet.getInt("reward_item_id");
                    boolean is_active = resultSet.getBoolean("is_active");
                    boolean is_delete = resultSet.getBoolean("is_delete");

                    return new MQuests(name, book_id, synopsis, request_item_id, reward_item_id, is_active, is_delete);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllQuestNames() {
        List<String> questNames = new ArrayList<>();

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT name FROM quests"))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    questNames.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch quest names in database.");
            e.printStackTrace();
        }

        return questNames;
    }

    public boolean updateQuest(MQuests quests){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update quests set " +
                             "name = ?," +
                             "book_id = ?," +
                             "synopsis = ?," +
                             "request_item_group_id = ?," +
                             "reward_item_group_id = ?," +
                             "is_active = ?," +
                             "is_delete = ?" +
                             " WHERE name = ?"))
        {
            statement.setString(1, quests.getName());
            statement.setString(2, quests.getBookId());
            statement.setString(3, quests.getSynopsis());
            statement.setInt(4, quests.getRequestItemId());
            statement.setInt(5, quests.getRewardItemId());
            statement.setBoolean(6, quests.getIsActive());
            statement.setBoolean(7, quests.getIsDelete());
            statement.setString(8, quests.getName());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update quest in database.");
            e.printStackTrace();
        }

        return false;
    }


}
