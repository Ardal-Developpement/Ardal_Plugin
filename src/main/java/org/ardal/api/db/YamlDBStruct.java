package org.ardal.api.db;

import org.ardal.Ardal;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class YamlDBStruct extends DBStruct{
    private FileConfiguration db;

    public YamlDBStruct(Path pluginDirPath, String dbFileName) {
        super(pluginDirPath, dbFileName);
    }
    @Override
    public void loadDB() {
        this.db = YamlConfiguration.loadConfiguration(this.getCustomItemFile());
    }

    @Override
    public void saveDB() {
        try {
            this.db.save(this.getCustomItemFile());
        } catch (IOException e) {
            Ardal.getInstance().getLogger().severe("Failed to save db: " + this.getDbFilePath());
        }
    }

    private File getCustomItemFile(){
        File file = new File(Ardal.getInstance().getDataFolder(), "CustomItem.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return file;
    }

    @Override
    public List<String> getKeySet() {
        return new ArrayList<>(this.db.getKeys(false));
    }

    public FileConfiguration getDB(){
        return this.db;
    }
}
