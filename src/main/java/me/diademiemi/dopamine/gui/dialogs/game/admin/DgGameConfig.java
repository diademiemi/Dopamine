package me.diademiemi.dopamine.gui.dialogs.game.admin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.dialogs.game.DgGameList;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class DgGameConfig implements Dialog {
    @Override
    public Menu create(Player p, Object... args) {
        Game game = (Game) args[0];
        MenuBuilder builder = new MenuBuilder("Config for game: " + game.getName());
        builder.setSize(MenuSize.TWO_ROWS);
        // 1st Slot
        builder.addButton(new GUIButton("Game Info", Material.BOOK, "View this games settings") {
            @Override
            public void onLeftClick(Player p) {
                new DgGameInfo().show(p, game);
            }
        }, 0);
        // 3rd Slot
        builder.addButton(new GUIButton("Set Icon", Material.PAINTING, "Set the icon of this game") {
            @Override
            public void onLeftClick(Player p) {
                new DgSetIcon().show(p, game);
            }
        }, 2);
        // 4th Slot
        builder.addButton(new GUIButton("Set Warp", Material.ENDER_PEARL, "Set the warp of this game") {
            @Override
            public void onLeftClick(Player p) {
                game.setWarp(p.getLocation());
                p.sendMessage("Warp set!");
            }
        }, 3);

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
        // 5th Slot
        if (selection != null) {
            builder.addButton(new GUIButton("Set Region", Material.WOODEN_AXE, "Set the region of this game", "Requires WorldEdit selection") {
                @Override
                public void onLeftClick(Player p) {
                    game.setRegion(p);
                    p.sendMessage("Region set!");
                }
            }, 4);
        } else {
            builder.addButton(new GUIButton("Set Region", Material.BARRIER, "Set the region of this game", "No WorldEdit selection currently active!"), 4);
        }
        // Add panes
        builder.addButton(new GUIButton(), 9, 10, 11, 12, 14, 15, 16, 17);
        // Return to game list
        builder.addButton(new GUIButton("Return", Material.MAGENTA_GLAZED_TERRACOTTA, "Return to game list") {
            @Override
            public void onLeftClick(Player p) {
                new DgGameList().show(p, 0);
            }
        }, 13);
        return builder.build(p);
    }
}
