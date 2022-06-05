package me.diademiemi.dopamine.gui.dialogs.game;

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
        builder.addButton(1, new GUIButton("Confirm", Material.LIME_WOOL, 1, "Your input was: ", input) {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                p.sendMessage("TESTING. You inputted " + input);
            }
        });
        builder.addButton(3, new GUIButton("Cancel", Material.RED_WOOL, 1, "Return to main menu") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                MainDialog.showDialog(p);
            }
        });
        builder.build(p).open();
    }
}

