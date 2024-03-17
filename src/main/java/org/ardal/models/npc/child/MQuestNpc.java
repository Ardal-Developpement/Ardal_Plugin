package org.ardal.models.npc.child;

import org.ardal.Ardal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MQuestNpc {
    private String uuid;
    private String quest_name;
    private int quest_coef;
    private boolean is_show;

    public MQuestNpc(String uuid, String quest_name, int quest_coef, boolean is_show) {
        this.uuid = uuid;
        this.quest_name = quest_name;
        this.quest_coef = quest_coef;
        this.is_show = is_show;
    }

    @Nullable
    public void createQuestNpc(@NotNull MQuestNpc mQuestNpc) throws SQLException {
        PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                .prepareStatement("insert into quest_npc(uuid, quest_name, quest_coef, is_show) values (?,?,?,?)");

        statement.setString(1, mQuestNpc.uuid);
        statement.setString(2, mQuestNpc.quest_name);
        statement.setInt(3, mQuestNpc.quest_coef);
        statement.setBoolean(4, mQuestNpc.is_show);

        statement.execute();
        statement.close();
    }

    @Nullable
    public MQuestNpc findQuestNpcByUuid(@NotNull String uuid){
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT uuid, quest_name, quest_coef, is_show FROM quest_npc WHERE uuid = ?"))
        {

            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                   String quest_name = resultSet.getString("quest_name");
                   int quest_coef = resultSet.getInt("quest_coef");
                   boolean is_show = resultSet.getBoolean("is_show");

                   return new MQuestNpc(uuid, quest_name, quest_coef, is_show);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQuest_name() {
        return quest_name;
    }

    public void setQuest_name(String quest_name) {
        this.quest_name = quest_name;
    }

    public int getQuest_coef() {
        return quest_coef;
    }

    public void setQuest_coef(int quest_coef) {
        this.quest_coef = quest_coef;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }
}
