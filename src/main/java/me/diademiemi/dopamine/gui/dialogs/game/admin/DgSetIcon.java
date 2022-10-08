package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import me.diademiemi.dopamine.lang.Button;
import me.diademiemi.dopamine.lang.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;


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
        MenuBuilder builder = new MenuBuilder(Title.get("set-icon"));
        builder.setSize(MenuSize.HALF_ROW);
        builder.addButton(new GUIButton(icon, Button.get("config-game-icon-current")) {
            @Override
            public void onItemDrag(Player p, Material m) {
                game.setIcon(m);
                show(p, game);
            }
        }, 2);
        builder.addButton(new GUIButton(Material.LIME_WOOL, Button.get("return-to-game-config")) {
            @Override
            public void onLeftClick(Player p) {
                new DgGameConfig().show(p, game);
            }
        }, 0);
        return builder.build(player);
    }

}
