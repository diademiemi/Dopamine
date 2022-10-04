package me.diademiemi.dopamine.gui.dialogs;

import me.diademiemi.dopamine.config.Config;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.game.GameListDialog;
import me.diademiemi.dopamine.gui.dialogs.game.admin.ReorderGameList;
import me.diademiemi.dopamine.gui.dialogs.inputs.CreateGameInput;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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

        builder.addButton(new GUIButton("List Games", Material.PAPER, 1, "This is the default option for non-admins") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                GameListDialog.showDialog(p, 0);
            }
        }, 4);

        builder.addButton(new GUIButton("Reorder game list", Material.COMPARATOR, 1, "Reorder the game list") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                ReorderGameList.showDialog(p, 0);
            }
        }, 6);
        builder.build(p).open();
    }
}

