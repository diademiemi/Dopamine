package me.diademiemi.dopamine.game;

import java.util.HashMap;

import org.bukkit.Material;

public class Game {
    public static HashMap<String, Game> games = new HashMap<String, Game>();

    private String name;

    private double[][] area;

    private Material icon;

    public static HashMap<String, Game> getGames() {
        return games;
    }

    public static Game getGame(String name) {
        return games.get(name);
    }

    public Game(String name, double[][] area, Material icon) {
        this.name = name;
        this.area = area;
        this.icon = icon;

        games.put(name, this);
    }
    public Game(String name) {
        this.name = name;
        this.icon = Material.JUKEBOX;
        games.put(name, this);
    }

    public Boolean isReady() {
        return (name != null && area != null && icon != null);
    }

    public String getName() {
        return name;
    }

    public double[][] getArea() {
        return area;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material m) {
        icon = m;
    }

}
