package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.MainDialog;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;


public class GameConfig {
    public static void showDialog(Player p, Game g) {
        MenuBuilder builder = new MenuBuilder("Config for game: " + g.getName());
        builder.setSize(MenuSize.TWO_ROWS);
        builder.addButton(new GUIButton("Set Icon", g.getIcon(), "Set the icon of this game") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                SetIcon.showDialog(p, g);
            }
        }, 4);
        builder.addButton(new GUIButton(), 9, 10, 11, 12, 14, 15, 16, 17);
        builder.addButton(new GUIButton("Return", Material.PINK_GLAZED_TERRACOTTA, "Return to game list") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                MainDialog.showDialog(p);
            }
        }, 13);
        builder.build(p).open();
    }
}
