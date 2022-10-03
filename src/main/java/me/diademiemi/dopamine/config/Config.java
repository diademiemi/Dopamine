package me.diademiemi.dopamine.config;

import me.diademiemi.dopamine.Dopamine;
import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.game.GameList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;

public class Config {

    private static File configFile;

    private static YamlConfiguration config;

    public static void init() {
        configFile = new File(Dopamine.getPlugin().getDataFolder(), "games.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Dopamine.getPlugin().saveResource("games.yml", false);
        }
    }

    public static void writeConfig() {
        config.set("games", GameList.getGames());
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        try {
            for (String key : config.getConfigurationSection("games").getKeys(false)) {
                GameList.addGame(key, (Game) config.get("games." + key));
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error loading games:");
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

}