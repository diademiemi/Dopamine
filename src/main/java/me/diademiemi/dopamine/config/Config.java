package me.diademiemi.dopamine.config;

import com.google.gson.reflect.TypeToken;
import me.diademiemi.dopamine.Dopamine;
import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.game.GameList;

import com.google.gson.*;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.*;
import java.util.HashMap;

public class Config {

    private static File configFile;

    public static void init() {
        configFile = new File(Dopamine.getPlugin().getDataFolder(), "games.json");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Dopamine.getPlugin().saveResource("games.json", false);
        }
    }
    public static void writeConfig() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        String json = gson.toJson(GameList.getGames());

        try {

            FileWriter jsonWriter = new FileWriter(configFile);

            jsonWriter.write(json);
            jsonWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadConfig() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        try {
            GameList.loadGames(gson.fromJson(new FileReader(configFile), new TypeToken<HashMap<String, Game>>(){}.getType()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}