package me.diademiemi.dopamine.lang;

import me.diademiemi.dopamine.Dopamine;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Lang {

    private static File langFile;

    private static YamlConfiguration language;

    private static HashMap<String, String> messages = new HashMap<>();

    private static HashMap<String, String> titles = new HashMap<>();

    private static HashMap<String, ArrayList<String>> buttons = new HashMap<>();

    private static String prefix;

    public static void init() {
        langFile = new File(Dopamine.getPlugin().getDataFolder(), "lang.yml");
        Dopamine.getPlugin().saveResource("lang.yml", true);
    }

    public static void loadConfig() {
        language = YamlConfiguration.loadConfiguration(langFile);
        try {
            for (String key : language.getConfigurationSection("messages").getKeys(false)) {
                messages.put(key, language.getString("messages." + key));
            }
            for (String key : language.getConfigurationSection("titles").getKeys(false)) {
                titles.put(key, language.getString("titles." + key));
            }
            for (String key : language.getConfigurationSection("buttons").getKeys(false)) {
                ArrayList<String> button = new ArrayList<String>();
                if (language.getString("buttons." + key + ".title") != null) {
                    button.add(0, language.getString("buttons." + key + ".title"));
                } else {
                    button.add(0, "Error: No title");
                }
                for (int i = 0; i < language.getStringList("buttons." + key + ".lore").size(); i++) {
                    button.add(i + 1, language.getStringList("buttons." + key + ".lore").get(i));
                }
                buttons.put(key, button);
            }

            prefix = language.getString("prefix");
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error loading messages:");
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

    public static String getMessage(String key) {
        return messages.get(key);
    }

    public static String getTitle(String key) {
        return titles.get(key);
    }

    public static ArrayList<String> getButton(String key) {
        return buttons.get(key);
    }

    public static String getPrefix() {
        return prefix;
    }

}
