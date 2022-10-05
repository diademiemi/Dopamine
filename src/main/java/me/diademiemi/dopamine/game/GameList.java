package me.diademiemi.dopamine.game;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;

import java.util.HashMap;

public class GameList {

    public static HashMap<String, Game> games = new HashMap<String, Game>();

    public static HashMap<Integer, String> gameOrder = new HashMap<Integer, String>();

    public static void setGamePosition(String gameName, int position) {
        gameOrder.put(position, gameName);
    }

    public static HashMap<Integer, String> getGameOrder() {
        return gameOrder;
    }

    public static void setGameOrder(HashMap<Integer, String> gameOrder) {
        for (int i : gameOrder.keySet()) {
            if (gameOrder.get(i) != null) {
                games.get(gameOrder.get(i)).setPosition(i);
            }
        }
        GameList.gameOrder = gameOrder;
    }

    public static Integer getGameIndex(String game, HashMap<Integer, String> order) {
        for (int i : order.keySet()) {
            if (order.get(i) != null && order.get(i).equals(game)) {
                return i;
            }
        }
        return null;
    }

    public static Game getGameByRegion(CuboidRegion region, Game exclude) {
        // Check for overlapping regions
        for (Game game : games.values()) {
            if ( game.getRegion() != null &&
                game.getRegion().getMinimumPoint().getX() <= region.getMaximumPoint().getX() &&
                game.getRegion().getMaximumPoint().getX() >= region.getMinimumPoint().getX() &&
                game.getRegion().getMinimumPoint().getY() <= region.getMaximumPoint().getY() &&
                game.getRegion().getMaximumPoint().getY() >= region.getMinimumPoint().getY() &&
                game.getRegion().getMinimumPoint().getZ() <= region.getMaximumPoint().getZ() &&
                game.getRegion().getMaximumPoint().getZ() >= region.getMinimumPoint().getZ()
            ) {
                if (exclude != null && game != exclude) {
                    return game;
                }
            }
        }
        return null;
    }

    public static Game getGameByRegion(CuboidRegion region) {
        return getGameByRegion(region, null);
    }

        public static Game getGameByLocation(Location location) {
        // Check for overlapping regions
        for (Game game : games.values()) {
            if (game.getRegion() != null && game.getRegion().contains(BukkitAdapter.asBlockVector(location))) {
                return game;
            }
        }
        return null;
    }

    public static Integer getGameIndex(String game) {
        return getGameIndex(game, gameOrder);
    }

    public static HashMap<String, Game> getGames() {
        return games;
    }

    public static void loadGames(HashMap<String, Game> games) {
        GameList.games = games;
    }

    public static Game getGame(String name) {
        return games.get(name.toLowerCase());
    }

    public static Game getGame(int position) {
        return games.get(gameOrder.get(position));
    }

    public static void addGame(String name, Game game) {
        games.put(name.toLowerCase(), game);
    }
}