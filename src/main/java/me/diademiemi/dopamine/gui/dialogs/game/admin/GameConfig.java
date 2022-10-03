package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.MainAdminDialog;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;

import org.bukkit.Bukkit;
import org.bukkit.Material;


public class GameConfig {
    public static void showDialog(Player p, Game g) {
        MenuBuilder builder = new MenuBuilder("Config for game: " + g.getName());
        builder.setSize(MenuSize.TWO_ROWS);
        // Set icon button
        builder.addButton(new GUIButton("Set Icon", g.getIcon(), "Set the icon of this game") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                SetIcon.showDialog(p, g);
            }
        }, 2);
        // Test for WorldEdit selection
        Region selection;
        try {
            WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
            if (worldEdit != null) {
                selection = worldEdit.getSession(p).getSelection(worldEdit.getSession(p).getSelectionWorld());
            } else {
                selection = null;
            }
        } catch (Exception e) {
            selection = null;
        }
        // Show button if there is a WorldEdit selection
        if (selection != null) {
            builder.addButton(new GUIButton("Set Region", Material.WOODEN_AXE, "Set the region of this game", "Requires WorldEdit selection") {
                @Override
                public void onLeftClick(Player p) {
                    g.setRegion(p);
                    p.sendMessage("Region set!");
                }
            }, 4);
        } else {
            builder.addButton(new GUIButton("Set Region", Material.RED_WOOL, "Set the region of this game", "No WorldEdit selection currently active!"), 4);
        }
        builder.addButton(new GUIButton("Game Info", Material.BOOK, "View this games settings") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                GameInfo.showDialog(p, g);
            }
        }, 0);
        // Add panes
        builder.addButton(new GUIButton(), 9, 10, 11, 12, 14, 15, 16, 17);
        // Return to game list
        builder.addButton(new GUIButton("Return", Material.MAGENTA_GLAZED_TERRACOTTA, "Return to game list") {
            @Override
            public void onLeftClick(Player p) {
                GUI.getGUI(p).close();
                MainAdminDialog.showDialog(p);
            }
        }, 13);
        builder.build(p).open();
    }
}
