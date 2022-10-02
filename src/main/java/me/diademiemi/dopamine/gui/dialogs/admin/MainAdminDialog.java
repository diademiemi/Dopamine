package me.diademiemi.dopamine.gui.dialogs;

import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.game.GameListDialog;
import me.diademiemi.dopamine.gui.dialogs.inputs.CreateGameInput;
import me.diademiemi.dopamine.gui.menu.*;
import me.diademiemi.dopamine.config.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;

public class MainAdminDialog {
    public static void showDialog(Player p) {
        MenuBuilder builder = new MenuBuilder("Dopamine");
        builder.setSize(MenuSize.ONE_ROW);
        builder.addButton(new GUIButton("Create Game", Material.REDSTONE, 1, "Start the new game creation process") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                new CreateGameInput(p);
            }
        }, 2);

        builder.addButton(new GUIButton("Save Config", Material.CHEST, 1, "Save configuration to disk") {
            @Override
            public void onLeftClick(Player p) {
                Config.writeConfig();
                p.sendMessage("Saved!");
            }
        }, 8);


        builder.addButton(new GUIButton("List Games", Material.PAPER, 1) {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                GameListDialog.showDialog(p, 0);
            }
        }, 4);
        builder.build(p).open();
    }
}

