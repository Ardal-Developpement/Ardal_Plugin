package org.ardal.db;

import org.ardal.Ardal;
import org.ardal.db.tables.TPlayer;
import org.ardal.db.tables.TQuest;
import org.ardal.db.tables.TQuestPlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:mysql://localhost/ardal";
    private static final String USER = "root";
    private static final String PASSWORD = "";



    private final TPlayer tPlayer;
    private final TQuest tQuest;
    private final TQuestPlayer tQuestPlayer;

    public Database(){
        this.tQuest = new TQuest();
        this.tQuestPlayer = new TQuestPlayer();
        this.tPlayer = new TPlayer();
    }
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if(this.connection != null){
            return this.connection;
        }

        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Ardal.writeToLogger("Success to establish connexion with database.");

        return this.connection;
    }

    public void initDb(){
        try {
            this.getConnection();
        } catch (SQLException e) {
            Ardal.writeToLogger("Unable to connect to database.");
            e.printStackTrace();
            return;
        }

        try {
            Statement statement = this.connection.createStatement();

            String sql = "create table if not exists player(" +
                    "uuid varchar(36) primary key,  " +
                    "name varchar(255), " +
                    "adventure_level int," +
                    "quest_cooldown datetime null)";
            statement.execute(sql);

            sql = "create table if not exists quest(" +
                    "id int auto_increment primary key," +
                    "name varchar(255)," +
                    "book_id int," +
                    "request_item_group_id int," +
                    "reward_item_group_id int," +
                    "is_active bool," +
                    "is_delete bool)";
            statement.execute(sql);

            sql = "create table if not exists npc(" +
                    "uuid varchar(36) primary key," +
                    "name varchar(255)," +
                    "is_visible bool," +
                    "location_id int," +
                    "type varchar(127))";
            statement.execute(sql);

            sql = "create table if not exists quest_npc(" +
                    "uuid varchar(36) primary key," +
                    "quest_name varchar(255)," +
                    "quest_coef int," +
                    "is_show bool)";
            statement.execute(sql);

            sql = "create table if not exists location(" +
                    "id int auto_increment primary key," +
                    "world_uuid varchar(36)," +
                    "x double," +
                    "y double," +
                    "z double," +
                    "yaw double," +
                    "pitch double)";
            statement.execute(sql);

            sql = "create table if not exists quest_player(" +
                    "quest_id int," +
                    "player_uuid varchar(36)," +
                    "is_finished bool," +
                    "start_date datetime)";
            statement.execute(sql);

            sql = "create table if not exists item_group(" +
                    "id int primary key," +
                    "item_group_id int," +
                    "item_id varchar(40))";
            statement.execute(sql);

            statement.close();
            this.connection.close();
            Ardal.writeToLogger("Created database tables.");

        } catch (SQLException e) {
            Ardal.writeToLogger("Unable to create tables in the database.");
            throw new RuntimeException(e);
        }
    }

    public TPlayer gettPlayer() {
        return tPlayer;
    }

    public TQuest gettQuest() {
        return tQuest;
    }

    public TQuestPlayer gettQuestPlayer() {
        return tQuestPlayer;
    }
}
