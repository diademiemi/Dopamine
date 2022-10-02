package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.MainAdminDialog;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;


public class SetIcon {
    public static void showDialog(Player p, Game g) {
        Material icon;
        if (g.getIcon() == null) {
            icon = Material.JUKEBOX;
        } else {
            icon = g.getIcon();
        }
        MenuBuilder builder = new MenuBuilder("Set Icon");
        builder.setSize(MenuSize.HALF_ROW);
        builder.addButton(new GUIButton("Current Icon", icon, "Current icon of the game", "Drag an item from your hotbar into this button") {
            @Override
            public void onItemDrag(Player p, Material m) {
                g.setIcon(m);
                GUI.getGUI(p).close();
                SetIcon.showDialog(p, g);
            }
        }, 2);
        builder.addButton(new GUIButton("Confirm", Material.LIME_WOOL, "Return to game config") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                MainAdminDialog.showDialog(p);
            }
        }, 0);
        builder.build(p).open();
    }

}
