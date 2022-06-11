package me.diademiemi.dopamine.game;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;

public class Game {
    public static HashMap<String, Game> games = new HashMap<String, Game>();

    public static HashMap<String, Game> getGames() {
        return games;
    }

    public static Game getGame(String name) {
        return games.get(name);
    }

    private String name;

    private double[][] region;

    private World world;

    private Material icon;


    public Game(String name, Material icon, double[][] region, World world) {
        this.name = name;
        this.region = region;
        this.icon = icon;

        games.put(name, this);
    }

    public Game(String name) {
        new Game(name, Material.JUKEBOX, new double[][] { { 0, 0, 0 }, { 0, 0, 0 } }, Bukkit.getWorlds().get(0));
    }

    public Boolean isReady() {
        return (name != null && region != null && icon != null);
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the region of this game
     *
     * @return Double of the region of this game
     */
    public double[][] getRegion() {
        return region;
    }

    /**
     * Use WorldEdit to set the region of this game
     *
     * @param player   Player to get WorldEdit selection from
     */
    public void setRegion(Player player) {
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        Region selection;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            player.sendMessage("No active WorldEdit selection");
            return;
        }

        if (selection != null) {
            region[0][0] = selection.getMinimumPoint().getX();
            region[0][1] = selection.getMinimumPoint().getY();
            region[0][2] = selection.getMinimumPoint().getZ();
            region[1][0] = selection.getMaximumPoint().getX();
            region[1][1] = selection.getMaximumPoint().getY();
            region[1][2] = selection.getMaximumPoint().getZ();
            world = BukkitAdapter.adapt(selection.getWorld());

        }
    }

    /**
     * Sets the region of this game
     *
     * @param region   Double of the region of this game
     */
    public void setRegion(double[][] region) {
        this.region = region;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material m) {
        icon = m;
    }

}
