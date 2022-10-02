package me.diademiemi.dopamine;

import me.diademiemi.dopamine.config.Config;
import me.diademiemi.dopamine.gui.GUIListener;
import me.diademiemi.dopamine.gui.input.InputListener;
import me.diademiemi.dopamine.command.CommandHandler;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Dopamine extends JavaPlugin {
    private static Dopamine plugin;

    private static PluginManager pm;

    public void onEnable() {
        plugin = this;
        Config.init();
        Config.loadConfig();
        pm = getServer().getPluginManager();

        getCommand("dopamine").setExecutor(new CommandHandler());
        pm.registerEvents(new GUIListener(), this);
        pm.registerEvents(new InputListener(), this);
    }

    public void onDisable() {
        plugin = null;
    }

    public static Dopamine getPlugin() {
        return plugin;
    }

}
