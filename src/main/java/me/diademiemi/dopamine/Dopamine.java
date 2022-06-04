package me.diademiemi.dopamine;

import me.diademiemi.dopamine.command.CommandHandler;
import me.diademiemi.dopamine.gui.GUIListener;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Dopamine extends JavaPlugin {
    private static Dopamine plugin;

    private static PluginManager pm;

    public void onEnable() {
        plugin = this;
        pm = getServer().getPluginManager();

        getCommand("dopamine").setExecutor(new CommandHandler());
        pm.registerEvents(new GUIListener(), this);
    }

    public static Dopamine getPlugin() {
        return plugin;
    }

}
