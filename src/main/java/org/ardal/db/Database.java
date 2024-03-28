package org.ardal.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.ardal.Ardal;
import org.ardal.db.tables.*;
import org.ardal.db.tables.npc.TNpc;
import org.ardal.db.tables.npc.type.TQuestNpc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:mysql://localhost/ardal?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private final HikariDataSource dataSource;

    private final TPlayer tPlayers;
    private final TQuest tQuests;
    private final TQuestPlayer tQuestPlayer;
    private final TGroup tGroup;
    private final TItemGroup tItemGroup;
    private final TNpc tNpc;
    private final TLocation tLocation;
    private final TQuestNpc tQuestNpc;

    public Database(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        dataSource = new HikariDataSource(config);

        this.tQuests = new TQuest();
        this.tQuestPlayer = new TQuestPlayer();
        this.tPlayers = new TPlayer();
        this.tGroup = new TGroup();
        this.tItemGroup = new TItemGroup();
        this.tNpc = new TNpc();
        this.tLocation = new TLocation();
        this.tQuestNpc = new TQuestNpc();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    public void initDb(){
        try {
            this.getConnection();
        } catch (SQLException e) {
            Ardal.writeToLogger("Unable to connect to database.");
            e.printStackTrace();
            return;
        }

        try (Connection connection = this.getConnection();
             Statement statement = connection.createStatement();)
        {
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
                    "id int primary key auto_increment," +
                    "npc_uuid varchar(36)," +
                    "quest_id int," +
                    "quest_coef int," +
                    "is_show bool," +
                    "foreign key (npc_uuid) references npcs(uuid) on delete cascade on update cascade," +
                    "foreign key (quest_id) references quests(id) on delete cascade)";
            statement.execute(sql);

            sql = "create table if not exists locations(" +
                    "id int auto_increment primary key," +
                    "world_uuid varchar(36)," +
                    "x double," +
                    "y double," +
                    "z double," +
                    "yaw float," +
                    "pitch float)";
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
            Ardal.writeToLogger("Created database tables.");
        } catch (SQLException e) {
            Ardal.writeToLogger("Unable to create tables in the database.");
            throw new RuntimeException(e);
        }
    }

    public void closeDataSource() {
        if(this.dataSource != null) {
            this.dataSource.close();
        }
    }

    public TPlayer gettPlayer() {
        return tPlayers;
    }

    public TQuest gettQuest() {
        return tQuests;
    }

    public TQuestPlayer gettQuestPlayer() {
        return tQuestPlayer;
    }

    public TGroup gettGroups() {
        return tGroup;
    }

    public TItemGroup gettItemGroup() {
        return tItemGroup;
    }

    public TNpc gettNpc() {
        return tNpc;
    }

    public TLocation gettLocation() {
        return tLocation;
    }

    public TQuestNpc gettQuestNpc() {
        return tQuestNpc;
    }
}
