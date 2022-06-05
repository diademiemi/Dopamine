package me.diademiemi.dopamine.gui.dialogs;

import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.inputs.CreateGameInput;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;

public class MainDialog {
    public static void showDialog(Player p) {
        MenuBuilder builder = new MenuBuilder("Dopamine");
        builder.setSize(MenuSize.ONE_ROW);
        builder.addButton(4, new GUIButton("Create Game", Material.REDSTONE, 1, "Start the new game creation process") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                new CreateGameInput(p);
            }
        });
        builder.build(p).open();
    }
}

