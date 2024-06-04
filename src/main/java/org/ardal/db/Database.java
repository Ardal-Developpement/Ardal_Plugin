package org.ardal.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.ardal.Ardal;
import org.ardal.db.tables.*;
import org.ardal.db.tables.chunk.TChunk;
import org.ardal.db.tables.chunk.TChunkMob;
import org.ardal.db.tables.npc.TNpc;
import org.ardal.db.tables.npc.type.TQuestNpc;
import org.ardal.db.tables.npc.type.TQuestNpcInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:mysql://{dbHost}/{dbName}?useSSL=false";

    private final HikariDataSource dataSource;

    private final TPlayer tPlayers;
    private final TQuest tQuests;
    private final TQuestPlayer tQuestPlayer;
    private final TGroup tGroup;
    private final TItemGroup tItemGroup;
    private final TNpc tNpc;
    private final TLocation tLocation;
    private final TQuestNpc tQuestNpc;
    private final TQuestNpcInfo tQuestNpcInfo;
    private final TAdventureLevel tAdventureLevel;
    private final TChunk tChunk;
    private final TChunkMob tChunkMob;

    private void checkDbConfigInit() {
        String dbHost = Ardal.getInstance().getConfig().getString("DB_HOST");
        String dbUser = Ardal.getInstance().getConfig().getString("DB_USER");
        String dbPassword = Ardal.getInstance().getConfig().getString("DB_PASSWORD");
        String dbName = Ardal.getInstance().getConfig().getString("DB_NAME");

        if(dbHost == null) {
            Ardal.getInstance().getConfig().set("DB_HOST", "");
        }

        if(dbUser == null) {
            Ardal.getInstance().getConfig().set("DB_USER", "");
        }

        if(dbPassword == null) {
            Ardal.getInstance().getConfig().set("DB_PASSWORD", "");
        }

        if(dbName == null) {
            Ardal.getInstance().getConfig().set("DB_NAME", "");
        }


        Ardal.getInstance().saveConfig();
    }

    public Database(){
        HikariConfig config = new HikariConfig();

        String dbHost = Ardal.getInstance().getConfig().getString("DB_HOST");
        if(dbHost == null || dbHost.isEmpty()){
            checkDbConfigInit();
            throw new RuntimeException("Please provide a mysql host in the config file.");
        }

        String dbUser = Ardal.getInstance().getConfig().getString("DB_USER");
        if(dbUser == null || dbUser.isEmpty()){
            checkDbConfigInit();
            throw new RuntimeException("Please provide a db user in the config file.");
        }

        String dbPassword = Ardal.getInstance().getConfig().getString("DB_PASSWORD");
        if(dbPassword == null || dbPassword.isEmpty()){
            checkDbConfigInit();
            throw new RuntimeException("Please provide a db password in the config file.");
        }

        String dbName = Ardal.getInstance().getConfig().getString("DB_NAME");
        if(dbName == null || dbName.isEmpty()){
            checkDbConfigInit();
            throw new RuntimeException("Please provide a db name in the config file.");
        }

        config.setJdbcUrl(URL.replace("{dbHost}", dbHost).replace("{dbName}", dbName));
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.setLeakDetectionThreshold(3000); // TODO DEBUG
        config.setConnectionTimeout(3000); // TODO DEBUG
        dataSource = new HikariDataSource(config);

        this.tQuests = new TQuest();
        this.tQuestPlayer = new TQuestPlayer();
        this.tPlayers = new TPlayer();
        this.tGroup = new TGroup();
        this.tItemGroup = new TItemGroup();
        this.tNpc = new TNpc();
        this.tLocation = new TLocation();
        this.tQuestNpc = new TQuestNpc();
        this.tQuestNpcInfo = new TQuestNpcInfo();
        this.tAdventureLevel = new TAdventureLevel();
        this.tChunk = new TChunk();
        this.tChunkMob = new TChunkMob();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    public void initDb(){
        try (Connection connection = this.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "create table if not exists players(" +
                        "uuid varchar(36) primary key,  " +
                        "name varchar(255), " +
                        "adventure_level int," +
                        "adventure_xp int," +
                        "quest_cooldown datetime null)";
                statement.execute(sql);

                sql = "create table if not exists adventure_level(" +
                        "level int primary key, " +
                        "name varchar(255), " +
                        "xp_required int)";
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

                sql = "create table if not exists locations(" +
                        "id int auto_increment primary key," +
                        "world_uuid varchar(36)," +
                        "x double," +
                        "y double," +
                        "z double," +
                        "yaw float," +
                        "pitch float)";
                statement.execute(sql);

                sql = "create table if not exists npcs(" +
                        "uuid varchar(36) primary key," +
                        "name varchar(255)," +
                        "skin_name varchar(255), " +
                        "is_visible bool," +
                        "location_id int," +
                        "type varchar(127)," +
                        "foreign key (location_id) references locations(id) on delete cascade)";
                statement.execute(sql);

                sql = "create table if not exists quest_npc_info(" +
                        "npc_uuid varchar(36)," +
                        "nb_quest_show int," +
                        "foreign key (npc_uuid) references npcs(uuid) on delete cascade on update cascade)";
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

                sql = "create table if not exists chunks(" +
                        "id int auto_increment primary key," +
                        "chunk_id long, " +
                        "chunk_group_id int)";
                statement.execute(sql);

                sql = "create table if not exists chunk_mob(" +
                        "chunk_id_group int," +
                        "mob_type varchar(127)," +
                        "level int," +
                        "cooldown float)";
                statement.execute(sql);

                statement.close();

                Ardal.writeToLogger("Created database tables.");
            } catch (SQLException e) {
                Ardal.writeToLogger("Unable to create tables in the database.");
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Unable to connect to database.");
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

    public TQuestNpcInfo gettQuestNpcInfo() {
        return tQuestNpcInfo;
    }

    public TAdventureLevel gettAdventureLevel() {
        return tAdventureLevel;
    }

    public TChunk gettChunk() {
        return tChunk;
    }

    public TChunkMob gettChunkMob() {
        return tChunkMob;
    }
}
