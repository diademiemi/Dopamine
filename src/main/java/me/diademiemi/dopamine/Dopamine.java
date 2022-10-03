package me.diademiemi.dopamine;

import me.diademiemi.dopamine.config.Config;
import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.game.GameList;
import me.diademiemi.dopamine.gui.GUIListener;
import me.diademiemi.dopamine.gui.input.InputListener;
import me.diademiemi.dopamine.command.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Dopamine extends JavaPlugin {

    static {
        ConfigurationSerialization.registerClass(Game.class);
    }

    private static Dopamine plugin;

    private static PluginManager pm;

    public void onEnable() {
        plugin = this;
        Config.init();
        Config.loadConfig();

        Bukkit.getLogger().info("Dopamine has been enabled with the following games:");
        for (Game value : GameList.getGames().values()) {
            Bukkit.getLogger().info("- " + value.getName());
        }
        pm = getServer().getPluginManager();

        getCommand("dopamine").setExecutor(new CommandHandler());
        pm.registerEvents(new GUIListener(), this);
        pm.registerEvents(new InputListener(), this);
    }

    public void onDisable() {
        Config.writeConfig();
        plugin = null;
    }

    public static Dopamine getPlugin() {
        return plugin;
    }

}
