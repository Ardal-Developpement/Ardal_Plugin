package org.ardal.api.db;

import org.bukkit.configuration.file.FileConfiguration;

import java.nio.file.Path;
import java.util.List;

public abstract class DBStruct {
    private FileConfiguration db;
    private final String dbFileName;
    private final Path dbFilePath;

    public DBStruct(Path pluginDirPath, String dbFileName){
        this.dbFileName = dbFileName;
        this.dbFilePath = pluginDirPath.resolve(this.dbFileName);
        this.loadDB();
    }

    public abstract void loadDB();

    public abstract void saveDB();

    public abstract List<String> getKeySet();

    public String getDbFileName() {
        return dbFileName;
    }

    public Path getDbFilePath() {
        return dbFilePath;
    }
}
