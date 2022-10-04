package me.diademiemi.dopamine.gui.dialogs.game;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.game.GameList;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.MainAdminDialog;
import me.diademiemi.dopamine.gui.dialogs.game.admin.GameConfig;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;

public class GameListDialog {
    public static void showDialog(Player p, int page) {
        MenuBuilder builder = new MenuBuilder("List of games");
        builder.setSize(MenuSize.FOUR_ROWS);

        int i = 0 + (page * 27);
        for (Game game : GameList.getGames().values()) {

            if (p.hasPermission("dopamine.config.games")) {
                builder.addButton( new GUIButton(game.getName(), game.getIcon(), "Right click to configure this game") {

                    @Override
                    public void onLeftClick(Player p) {
                        GUI.getGUI(p).close();
                        p.teleport(game.getWarp());
                    }
                    @Override
                    public void onRightClick(Player p) {
                        GUI.getGUI(p).close();
                        GameConfig.showDialog(p, game);
                    }
                }, i);
            } else {
                builder.addButton( new GUIButton(game.getName(), game.getIcon()) {

                    @Override
                    public void onLeftClick(Player p) {
                        GUI.getGUI(p).close();
                        p.teleport(game.getWarp());
                    }
                }, i);
            }

            i++;
        }
        builder.addButton(new GUIButton("Previous page", Material.RED_STAINED_GLASS_PANE) {
            @Override
            public void onLeftClick(Player p) {
                if (page > 0) {
                    GUI.getGUI(p).close();
                    showDialog(p, page - 1);
                }
            }
        }, 28);
        builder.addButton(new GUIButton("Return to main menu", Material.BARRIER) {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                MainAdminDialog.showDialog(p);
            }
        }, 31);
        builder.addButton(new GUIButton("Next page", Material.LIME_STAINED_GLASS_PANE) {
            @Override
            public void onLeftClick(Player p) {
                if (page < GameList.getGames().size() / 27) {
                    GUI.getGUI(p).close();
                    showDialog(p, page + 1);
                }
            }
        }, 34);
        builder.addButton(new GUIButton(), 27, 29, 30, 32, 33, 35);


        builder.build(p).open();
    }
}
