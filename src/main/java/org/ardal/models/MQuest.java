package org.ardal.models;

import org.ardal.Ardal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class MQuest {
    private String name;
    private int book_id;
    private int request_item_id;
    private int reward_item_id;
    private boolean is_active;
    private boolean is_delete;

    public MQuest(String name, int book_id, int request_item_id, int reward_item_id, boolean is_active, boolean is_delete) {
        this.name = name;
        this.book_id = book_id;
        this.request_item_id = request_item_id;
        this.reward_item_id = reward_item_id;
        this.is_active = is_active;
        this.is_delete = is_delete;
    }

    @Nullable
    public int createQuest(@NotNull MQuest mQuest) throws SQLException {
        PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                .prepareStatement("insert into quest(name, book_id, request_item_id, reward_item_id, is_active, is_delete) values (?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, mQuest.name);
        statement.setInt(2, mQuest.book_id);
        statement.setInt(3, mQuest.request_item_id);
        statement.setInt(4, mQuest.reward_item_id);
        statement.setBoolean(5, is_active);
        statement.setBoolean(6, is_delete);

        statement.execute();
        int id = statement.getGeneratedKeys().getInt(1);
        statement.close();

        return id;
    }

    @Nullable
    public MQuest findQuestByName(@NotNull String name){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT book_id, request_item_id, reward_item_id, is_active, is_delete FROM quest WHERE name = ?"))
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getRequest_item_id() {
        return request_item_id;
    }

    public void setRequest_item_id(int request_item_id) {
        this.request_item_id = request_item_id;
    }

    public int getReward_item_id() {
        return reward_item_id;
    }

    public void setReward_item_id(int reward_item_id) {
        this.reward_item_id = reward_item_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
