package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.MainDialog;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;


public class GameInfo {
    public static void showDialog(Player p, Game g) {
        MenuBuilder builder = new MenuBuilder("Confirm game creation");
        builder.setSize(MenuSize.TWO_ROWS);
        double[][] region = g.getRegion();
        builder.addButton(new GUIButton("Current Region", Material.WOODEN_AXE, 
        "Min: " + region[0][0] + ", " + region[0][1] + ", "  + region[0][2],
        "Max: " + region[1][0] + ", " + region[1][1] + ", "  + region[1][2]
        ), 1);
        builder.addButton(new GUIButton("Current Icon", g.getIcon(), 
        "Icon: " + g.getIcon().name()
        ), 0);
        // Add panes
        builder.addButton(new GUIButton(), 9, 10, 11, 12, 14, 15, 16, 17);
        // Return to game list
        builder.addButton(new GUIButton("Return", Material.MAGENTA_GLAZED_TERRACOTTA, "Return to game") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                GameConfig.showDialog(p, g);
            }
        }, 13);
        builder.build(p).open();
    }

}
