package me.bigvirusboi.potatowarp.data;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public static void setup() {
        File f = new File("plugins/PotatoWarp/warps.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
            yml.createSection("Warps");
            yml.options().copyDefaults(true);

            try {
                yml.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getWarpsFile() {
        return new File("plugins/PotatoWarp/warps.yml");
    }

    public static YamlConfiguration getWarpsConfig() {
        return YamlConfiguration.loadConfiguration(getWarpsFile());
    }

    public static void saveFile(YamlConfiguration yml) {
        try {
            yml.save(getWarpsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
