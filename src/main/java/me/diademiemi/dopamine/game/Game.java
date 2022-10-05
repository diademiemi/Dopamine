package me.diademiemi.dopamine.game;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class Game implements ConfigurationSerializable {

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("position", position);
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
        if (map.get("position") != null) {
            position = (Integer) map.get("position");
        } else {
            // Fallback for if the position is null
            int i = 0;
            while (GameList.getGame(i) != null) {
                i++;
            }
            position = i;
        }
        GameList.setGamePosition(name.toLowerCase(), position);
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

    private Integer position;

    private Material icon;

    private CuboidRegion region;

    private Location warp;

    public Game(String name, int position, Material icon, CuboidRegion region, Location warp) {
        this.name = name;
        this.position = position;
        this.region = region;
        this.icon = icon;
        this.warp = warp;

        GameList.addGame(name.toLowerCase(), this);
    }

    public Game(String name, Location warp) {
        int i = 0;
        while (GameList.getGame(i) != null) {
            i++;
        }

        new Game(name, i, Material.JUKEBOX, null, warp);

        GameList.setGamePosition(name.toLowerCase(), i);
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
            this.region = selection.clone();
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
