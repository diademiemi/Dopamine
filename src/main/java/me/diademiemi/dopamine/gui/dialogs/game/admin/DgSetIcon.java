package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.admin.DgMainAdmin;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;


public class DgSetIcon implements Dialog {
    @Override
    public Menu create(Player player, Object... args) {
        Game game = (Game) args[0];
        Material icon;
        if (game.getIcon() == null) {
            icon = Material.JUKEBOX;
        } else {
            icon = game.getIcon();
        }
        MenuBuilder builder = new MenuBuilder("Set Icon");
        builder.setSize(MenuSize.HALF_ROW);
        builder.addButton(new GUIButton("Current Icon", icon, "Current icon of the game", "Drag an item from your hotbar into this button") {
            @Override
            public void onItemDrag(Player p, Material m) {
                game.setIcon(m);
                show(p, game);
            }
        }, 2);
        builder.addButton(new GUIButton("Return to game config", Material.LIME_WOOL) {
            @Override
            public void onLeftClick(Player p) {
                new DgGameInfo().show(p, game);
            }
        }, 0);
        return builder.build(player);
    }

}
