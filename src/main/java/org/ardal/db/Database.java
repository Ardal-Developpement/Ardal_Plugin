package org.ardal.db;

import org.ardal.Ardal;
import org.ardal.db.tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:mysql://localhost/ardal";
    private static final String USER = "root";
    private static final String PASSWORD = "";



    private final TPlayers tPlayers;
    private final TQuests tQuests;
    private final TQuestPlayer tQuestPlayer;
    private final TGroups tGroups;
    private final TItemGroup tItemGroup;

    public Database(){
        this.tQuests = new TQuests();
        this.tQuestPlayer = new TQuestPlayer();
        this.tPlayers = new TPlayers();
        this.tGroups = new TGroups();
        this.tItemGroup = new TItemGroup();
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

            String sql = "create table if not exists players(" +
                    "uuid varchar(36) primary key,  " +
                    "name varchar(255), " +
                    "adventure_level int," +
                    "quest_cooldown datetime null)";
            statement.execute(sql);

            sql = "create table if not exists quests(" +
                    "id int auto_increment primary key," +
                    "name varchar(255)," +
                    "book_id varchar(40)," +
                    "synopsis text," +
                    "request_item_group_id int," +
                    "reward_item_group_id int," +
                    "is_active bool," +
                    "is_delete bool)";
            statement.execute(sql);

            sql = "create table if not exists npcs(" +
                    "uuid varchar(36) primary key," +
                    "name varchar(255)," +
                    "is_visible bool," +
                    "location_id int," +
                    "type varchar(127)," +
                    "foreign key (location_id) references locations(id) on delete cascade)";
            statement.execute(sql);

            sql = "create table if not exists quest_npc(" +
                    "uuid varchar(36) primary key," +
                    "quest_id int," +
                    "quest_coef int," +
                    "is_show bool," +
                    "foreign key (uuid) references npcs(uuid) on delete cascade," +
                    "foreign key (quest_id) references quests(id) on delete cascade)";
            statement.execute(sql);

            sql = "create table if not exists locations(" +
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
                    "start_date datetime," +
                    "foreign key (player_uuid) references players(uuid) on delete cascade," +
                    "foreign key (quest_id) references quests(id) on delete cascade )";
            statement.execute(sql);

            sql = "create table if not exists `groups`(" +
                    "id int auto_increment primary key)";
            statement.execute(sql);

            sql = "create table if not exists item_group(" +
                    "id int auto_increment primary key," +
                    "group_id int," +
                    "item_id varchar(40)," +
                    "foreign key (group_id) references `groups`(id) on delete cascade)";
            statement.execute(sql);

            statement.close();
            this.connection.close();
            Ardal.writeToLogger("Created database tables.");

        } catch (SQLException e) {
            Ardal.writeToLogger("Unable to create tables in the database.");
            throw new RuntimeException(e);
        }
    }

    public TPlayers gettPlayer() {
        return tPlayers;
    }

    public TQuests gettQuest() {
        return tQuests;
    }

    public TQuestPlayer gettQuestPlayer() {
        return tQuestPlayer;
    }

    public TGroups gettGroups() {
        return tGroups;
    }

    public TItemGroup gettItemGroup() {
        return tItemGroup;
    }
}
