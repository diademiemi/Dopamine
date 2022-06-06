package me.diademiemi.dopamine.gui.dialogs.game;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.MainDialog;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;

public class GameListDialog {
    public static void showDialog(Player p, int page) {
        MenuBuilder builder = new MenuBuilder("List of games");
        builder.setSize(MenuSize.FOUR_ROWS);

        int i = 0 + (page * 27);
        for (Game game : Game.getGames().values()) {
            builder.addButton( new GUIButton(game.getName(), game.getIcon(), 1) {
                @Override
                public void onLeftClick(Player p) {
                    GUI.getGUI(p).close();
                    p.sendMessage("Selected: " + game.getName());
                }
            }, i);
            i++;
        }

        builder.addButton(new GUIButton(), 27, 29, 30, 32, 33, 35);
        builder.addButton(new GUIButton("Previous page", Material.RED_STAINED_GLASS_PANE, 1) {
            @Override
            public void onLeftClick(Player p) {
                if (page > 0) {
                    GUI.getGUI(p).close();
                    showDialog(p, page - 1);
                }
            }
        }, 28);

        builder.addButton(new GUIButton("Return to main menu", Material.BARRIER, 1) {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                MainDialog.showDialog(p);
            }
        }, 31);

        builder.addButton(new GUIButton("Next page", Material.LIME_STAINED_GLASS_PANE, 1) {
            @Override
            public void onLeftClick(Player p) {
                if (page < Game.getGames().size() / 27) {
                    GUI.getGUI(p).close();
                    showDialog(p, page + 1);
                }
            }
        }, 34);


        builder.build(p).open();
    }
}
