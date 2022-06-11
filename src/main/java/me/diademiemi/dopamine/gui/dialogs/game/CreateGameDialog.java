package me.diademiemi.dopamine.gui.dialogs.game;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.MainDialog;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;

public class CreateGameDialog {
    public static void showDialog(Player p, String input) {
        MenuBuilder builder = new MenuBuilder("Confirm game creation");
        builder.setSize(MenuSize.HALF_ROW);
        builder.addButton( new GUIButton("Confirm", Material.LIME_WOOL, "Your input was: ", input) {
            @Override
            public void onLeftClick(Player p) {
                new Game(input);
                GUI.getGUI(p).close();
                GameListDialog.showDialog(p, 0);
            }
        }, 1);
        builder.addButton(new GUIButton("Cancel", Material.RED_WOOL, "Return to main menu") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                MainDialog.showDialog(p);
            }
        }, 3);
        builder.addButton(new GUIButton(), 0, 2, 4);
        builder.build(p).open();
    }
}

