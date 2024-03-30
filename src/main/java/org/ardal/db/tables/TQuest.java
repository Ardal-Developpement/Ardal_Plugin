package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.models.MQuest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TQuest {
    @Nullable
    public int saveQuest(@NotNull MQuest mQuest) {
        int id = -1;
        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into quests(name, book_id, request_item_group_id, reward_item_group_id, is_active, is_delete) values (?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, mQuest.getName());
            statement.setString(2, mQuest.getBookId());
            statement.setInt(3, mQuest.getRequestItemGroupId());
            statement.setInt(4, mQuest.getRewardItemGroupId());
            statement.setBoolean(5, mQuest.getIsActive());
            statement.setBoolean(6, mQuest.getIsDelete());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }

            statement.close();
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save quest in database.");
            e.printStackTrace();
        }

        return id;
    }

    public boolean deleteQuest(String questName) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from quests WHERE name = ?"))
        {
            statement.setString(1, questName);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete quest in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public MQuest getQuestByName(@NotNull String name, boolean includeDelete) {
        try {
            Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement;
             if(includeDelete) {
                 statement = connection.prepareStatement("SELECT * FROM quests WHERE name = ?");
             } else {
                 statement = connection.prepareStatement("SELECT * FROM quests WHERE name = ? and is_delete = false");
             }

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String book_id = resultSet.getString("book_id");
                    String synopsis = resultSet.getString("synopsis");
                    int request_item_group_id = resultSet.getInt("request_item_group_id");
                    int reward_item_group_id = resultSet.getInt("reward_item_group_id");
                    boolean is_active = resultSet.getBoolean("is_active");
                    boolean is_delete = resultSet.getBoolean("is_delete");

                    return new MQuest(id, name, book_id, synopsis, request_item_group_id, reward_item_group_id, is_active, is_delete);
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch quest by name in database.");
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public MQuest getQuestById(int questId, boolean includeDelete) {
        try {
            Connection connection = Ardal.getInstance().getDb().getConnection();
            PreparedStatement statement;
            if(includeDelete) {
                statement = connection.prepareStatement("SELECT name, book_id, synopsis, request_item_group_id, reward_item_group_id, is_active, is_delete FROM quests WHERE id = ?");
            } else {
                statement = connection.prepareStatement("SELECT name, book_id, synopsis, request_item_group_id, reward_item_group_id, is_active, is_delete FROM quests WHERE id = ? and is_delete = false");
            }

            statement.setInt(1, questId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String book_id = resultSet.getString("book_id");
                    String synopsis = resultSet.getString("synopsis");
                    int request_item_group_id = resultSet.getInt("request_item_group_id");
                    int reward_item_group_id = resultSet.getInt("reward_item_group_id");
                    boolean is_active = resultSet.getBoolean("is_active");
                    boolean is_delete = resultSet.getBoolean("is_delete");

                    return new MQuest(questId, name, book_id, synopsis, request_item_group_id, reward_item_group_id, is_active, is_delete);
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch quest by id in database.");
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public Integer getQuestIdByName(@NotNull String name, boolean includeDeleted) {
        try {
            Connection connection = Ardal.getInstance().getDb().getConnection();
            PreparedStatement statement;

            if(includeDeleted) {
                statement = connection.prepareStatement("SELECT id FROM quests WHERE name = ?");
            } else {
                statement = connection.prepareStatement("SELECT id FROM quests WHERE name = ? and is_delete = false");
            }

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

    public List<String> getAllQuestNames(boolean includeDeleted) {
        List<String> questNames = new ArrayList<>();

        try {
            Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement;

             if(includeDeleted) {
                 statement = connection.prepareStatement("select name from quests");
             } else {
                 statement = connection.prepareStatement("select name from quests where is_delete = false");
             }

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

    public boolean updateQuest(MQuest quests){
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
                             "where name = ?"))
        {
            statement.setString(1, quests.getName());
            statement.setString(2, quests.getBookId());
            statement.setString(3, quests.getSynopsis());
            statement.setInt(4, quests.getRequestItemGroupId());
            statement.setInt(5, quests.getRewardItemGroupId());
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
