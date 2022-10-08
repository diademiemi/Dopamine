package me.diademiemi.dopamine.gui.dialogs.admin;

import me.diademiemi.dopamine.game.GameIO;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.dialogs.admin.inputs.IptCreateGame;
import me.diademiemi.dopamine.gui.dialogs.game.DgGameList;
import me.diademiemi.dopamine.gui.dialogs.game.admin.DgReorderGameList;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import me.diademiemi.dopamine.lang.Button;
import me.diademiemi.dopamine.lang.Message;
import me.diademiemi.dopamine.lang.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DgMainAdmin implements Dialog {
    @Override
    public Menu create(Player p, Object... args) {
        MenuBuilder builder = new MenuBuilder(Title.get("admin-main-menu"));
        builder.setSize(MenuSize.ONE_ROW);
        builder.addButton(new GUIButton(Material.REDSTONE, 1, Button.get("create-game")) {
            @Override
            public void onLeftClick(Player p) {
                close(p);
                new IptCreateGame(p);
            }
        }, 2);

        builder.addButton(new GUIButton(Material.CHEST, 1, Button.get("save-config")) {
            @Override
            public void onLeftClick(Player p) {
                GameIO.writeConfig();
                Message.send(p, "config-saved-to-disk");
            }
        }, 8);

        builder.addButton(new GUIButton(Material.PAPER, 1, Button.get("game-list")) {
            @Override
            public void onLeftClick(Player p) {
                new DgGameList().show(p, 0);
            }
        }, 4);

        builder.addButton(new GUIButton(Material.COMPARATOR, 1, Button.get("game-reorder-list")) {
            @Override
            public void onLeftClick(Player p) {
                new DgReorderGameList().show(p, 0);
            }
        }, 6);
        return builder.build(p);
    }
}

