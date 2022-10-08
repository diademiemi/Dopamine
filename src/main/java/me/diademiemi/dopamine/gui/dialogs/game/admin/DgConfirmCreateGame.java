package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.dialogs.admin.DgMainAdmin;
import me.diademiemi.dopamine.gui.dialogs.game.DgGameList;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import me.diademiemi.dopamine.lang.Button;
import me.diademiemi.dopamine.lang.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DgConfirmCreateGame implements Dialog {

    @Override
    public Menu create(Player p, Object... args) {
        String input = (String) args[0];

        MenuBuilder builder = new MenuBuilder(Title.get("confirmation"));
        builder.setSize(MenuSize.HALF_ROW);
        builder.addButton( new GUIButton(Material.LIME_WOOL, Button.get("create-game-confirm", "name", input)) {
            @Override
            public void onLeftClick(Player p) {
                new Game(input, p.getLocation());
                new DgGameList().show(p, 0);
            }
        }, 1);
        builder.addButton(new GUIButton(Material.RED_WOOL, Button.get("create-game-cancel")) {
            @Override
            public void onLeftClick(Player p) {
                new DgMainAdmin().show(p);
            }
        }, 3);
        builder.addButton(new GUIButton(), 0, 2, 4);
        return builder.build(p);
    }
}

