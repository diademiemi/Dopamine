package me.diademiemi.dopamine.gui.dialogs.game.admin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.game.GameList;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.dialogs.game.DgGameList;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import me.diademiemi.dopamine.lang.Button;
import me.diademiemi.dopamine.lang.Message;
import me.diademiemi.dopamine.lang.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class DgGameConfig implements Dialog {
    @Override
    public Menu create(Player p, Object... args) {
        Game game = (Game) args[0];
        MenuBuilder builder = new MenuBuilder(Title.get("game-config", "name", game.getName()));
        builder.setSize(MenuSize.TWO_ROWS);
        // 1st Slot
        builder.addButton(new GUIButton(Material.BOOK, Button.get("config-game-info")) {
            @Override
            public void onLeftClick(Player p) {
                new DgGameInfo().show(p, game);
            }
        }, 0);
        // 3rd Slot
        builder.addButton(new GUIButton(Material.PAINTING, Button.get("config-game-icon")) {
            @Override
            public void onLeftClick(Player p) {
                new DgSetIcon().show(p, game);
            }
        }, 2);
        // 4th Slot
        builder.addButton(new GUIButton(Material.ENDER_PEARL,
                Button.get("config-game-warp",
                        "x", String.valueOf(p.getLocation().getBlockX()),
                        "y", String.valueOf(p.getLocation().getBlockY()),
                        "z", String.valueOf(p.getLocation().getBlockZ()))) {
            @Override
            public void onLeftClick(Player p) {
                game.setWarp(p.getLocation());
                Message.send(p, "config-game-warp-set");
            }
        }, 3);

        // Test for WorldEdit selection
        CuboidRegion selection = null;
        try {
            WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
            if (worldEdit != null) {
                selection = (CuboidRegion) worldEdit.getSession(p).getSelection(worldEdit.getSession(p).getSelectionWorld());
            }
        } catch (Exception e) {
            selection = null;
        }

        // 5th Slot
        if (selection != null) {
            // Check if region overlaps with any other game
            Game overlaps = GameList.getGameByRegion(selection, game);
            if (overlaps != null) {
                builder.addButton(new GUIButton(Material.BARRIER, Button.get("config-game-region-err-overlaps", "name", overlaps.getName())), 4);
            } else {
                builder.addButton(new GUIButton(Material.WOODEN_AXE, Button.get("config-game-region-set")) {
                    @Override
                    public void onLeftClick(Player p) {
                        game.setRegion(p);
                        Message.send(p, "config-game-region-set");
                    }
                }, 4);
            }
        } else {
            builder.addButton(new GUIButton(Material.BARRIER, Button.get("config-game-region-err-no-sel")), 4);
        }
        // Add panes
        builder.addButton(new GUIButton(), 9, 10, 11, 12, 14, 15, 16, 17);
        // Return to game list
        builder.addButton(new GUIButton(Material.MAGENTA_GLAZED_TERRACOTTA, Button.get("return-game-list")) {
            @Override
            public void onLeftClick(Player p) {
                new DgGameList().show(p, 0);
            }
        }, 13);
        return builder.build(p);
    }
}
