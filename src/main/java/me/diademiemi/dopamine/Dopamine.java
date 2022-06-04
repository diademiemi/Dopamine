package me.diademiemi.dopamine;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Dopamine extends JavaPlugin {
    private static Dopamine plugin;

    private static PluginManager pm;

    public void onEnable() {
        plugin = this;
        pm = getServer().getPluginManager();
    }
}
