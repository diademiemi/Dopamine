package me.diademiemi.dopamine;

import me.diademiemi.dopamine.command.CommandHandler;
import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.game.GameIO;
import me.diademiemi.dopamine.game.GameList;
import me.diademiemi.dopamine.gui.GUIListener;
import me.diademiemi.dopamine.gui.input.InputListener;
import me.diademiemi.dopamine.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Dopamine extends JavaPlugin {

    static {
        ConfigurationSerialization.registerClass(Game.class);
    }

    private static Dopamine plugin;

    private static String pluginName = "Dopamine";

    private static PluginManager pm;

    private static String[] permissions = new String[]{
            "admin",
            "admin.create",
            "admin.delete",
            "admin.reorder",
            "admin.edit",
            "admin.edit.name",
            "admin.edit.description",
            "admin.edit.region",
            "admin.edit.warp",
            "admin.edit.icon",
    };

    public void onEnable() {
        plugin = this;
        GameIO.init();
        GameIO.loadConfig();
        Lang.init();
        Lang.loadConfig();

        Bukkit.getLogger().info("Dopamine has been enabled with the following games:");
        for (Game value : GameList.getGames().values()) {
            Bukkit.getLogger().info("- " + value.getName());
        }

        pm = getServer().getPluginManager();

        for (String permission : permissions) {
            pm.addPermission(new org.bukkit.permissions.Permission(pluginName.toLowerCase() + "." + permission));
        }

        getCommand("dopamine").setExecutor(new CommandHandler());
        getCommand("games").setExecutor(new CommandHandler());
        pm.registerEvents(new GUIListener(), this);
        pm.registerEvents(new InputListener(), this);
    }

    public void onDisable() {
        GameIO.writeConfig();
        plugin = null;
    }

    public static Dopamine getPlugin() {
        return plugin;
    }

}
