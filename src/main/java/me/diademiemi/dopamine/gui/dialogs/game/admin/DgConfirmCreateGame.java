package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.dialogs.admin.DgMainAdmin;
import me.diademiemi.dopamine.gui.dialogs.game.DgGameList;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;

public class DgConfirmCreateGame implements Dialog {

    @Override
    public Menu create(Player p, Object... args) {
        String input = (String) args[0];

        MenuBuilder builder = new MenuBuilder("Confirm game creation");
        builder.setSize(MenuSize.HALF_ROW);
        builder.addButton( new GUIButton("Confirm", Material.LIME_WOOL, "Your input was: ", input) {
            @Override
            public void onLeftClick(Player p) {
                new Game(input, p.getLocation());
                new DgGameList().show(p, 0);
            }
        }, 1);
        builder.addButton(new GUIButton("Cancel", Material.RED_WOOL, "Return to main menu") {
            @Override
            public void onLeftClick(Player p) {
                new DgMainAdmin().show(p);
            }
        }, 3);
        builder.addButton(new GUIButton(), 0, 2, 4);
        return builder.build(p);
    }
}

