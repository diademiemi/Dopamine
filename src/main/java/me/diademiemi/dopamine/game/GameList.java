package me.diademiemi.dopamine.game;

import java.util.HashMap;

public class GameList {

    public static HashMap<String, Game> games = new HashMap<String, Game>();
    public static HashMap<String, Game> getGames() {
        return games;
    }

    public static void loadGames(HashMap<String, Game> games) {
        GameList.games = games;
    }

    public static Game getGame(String name) {
        return games.get(name.toLowerCase());
    }

    public static void addGame(String name, Game game) {
        games.put(name.toLowerCase(), game);
    }
}