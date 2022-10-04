package me.diademiemi.dopamine.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;


public class Game implements ConfigurationSerializable {

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("icon", icon.name());
        if (region != null) {
            Map<String, Object> regionMap = new HashMap<String, Object>();
            regionMap.put("minX", region.getMinimumPoint().getBlockX());
            regionMap.put("minY", region.getMinimumPoint().getBlockY());
            regionMap.put("minZ", region.getMinimumPoint().getBlockZ());
            regionMap.put("maxX", region.getMaximumPoint().getBlockX());
            regionMap.put("maxY", region.getMaximumPoint().getBlockY());
            regionMap.put("maxZ", region.getMaximumPoint().getBlockZ());
            regionMap.put("world", region.getWorld().getName());
            map.put("region", regionMap);
        } else {
            map.put("region", null);
        }
        map.put("warp", warp);
        return map;
    }

    // This is the constructor that is called when deserializing the object
    public Game(Map<String, Object> map) {
        name = (String) map.get("name");
        icon = (Material) Material.valueOf(map.get("icon").toString());
        if (map.get("region") != null) {
            region = new CuboidRegion(
                    BukkitAdapter.adapt(Bukkit.getWorld((String) ((Map<String, Object>) map.get("region")).get("world"))),
                    BlockVector3.at(
                            (int) ((Map<String, Object>) map.get("region")).get("minX"),
                            (int) ((Map<String, Object>) map.get("region")).get("minY"),
                            (int) ((Map<String, Object>) map.get("region")).get("minZ")
                    ),
                    BlockVector3.at(
                            (int) ((Map<String, Object>) map.get("region")).get("maxX"),
                            (int) ((Map<String, Object>) map.get("region")).get("maxY"),
                            (int) ((Map<String, Object>) map.get("region")).get("maxZ")
                    ));
        } else {
            region = null;
        }
        warp = (Location) map.get("warp");
    }

    private String name;

    private Material icon;

    private CuboidRegion region;

    private Location warp;

    public Game(String name, Material icon, CuboidRegion region, Location warp) {
        this.name = name;
        this.region = region;
        this.icon = icon;
        this.warp = warp;

        GameList.addGame(name.toLowerCase(), this);
    }

    public Game(String name, Location warp) {
        new Game(name, Material.JUKEBOX, null, warp);
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
    public CuboidRegion getRegion() {
        return region;
    }

    /**
     * Use WorldEdit to set the region of this game
     *
     * @param player   Player to get WorldEdit selection from
     */
    public void setRegion(Player player) {
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        CuboidRegion selection;
        try {
            selection = (CuboidRegion) worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            player.sendMessage("No active WorldEdit selection");
            return;
        }

        if (selection != null) {
            this.region = selection;
        }
    }

    /**
     * Sets the region of this game
     *
     * @param region   Double of the region of this game
     */
    public void setRegion(CuboidRegion region) {
        this.region = region;
    }


    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material m) {
        icon = m;
    }

    public Location getWarp() {
        return warp;
    }

    public void setWarp(Location l) {
        warp = l;
    }

}
