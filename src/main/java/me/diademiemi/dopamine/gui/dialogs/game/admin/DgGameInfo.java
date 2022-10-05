package me.diademiemi.dopamine.gui.dialogs.game.admin;

import com.sk89q.worldedit.regions.Region;
import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.menu.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;


public class DgGameInfo implements Dialog {
    @Override
    public Menu create(Player p, Object... args) {
        Game game = (Game) args[0];
        MenuBuilder builder = new MenuBuilder("Confirm game creation");
        builder.setSize(MenuSize.TWO_ROWS);
        Region region = game.getRegion();
        if (region != null) {
                builder.addButton(new GUIButton("Region", Material.GRASS_BLOCK, "Region: ", region.getMinimumPoint().getX() + ", " + region.getMinimumPoint().getY() + ", " + region.getMinimumPoint().getZ() + " - " + region.getMaximumPoint().getX() + ", " + region.getMaximumPoint().getY() + ", " + region.getMaximumPoint().getZ()) {
                @Override
                public void onLeftClick(Player p) {
                    new DgGameConfig().show(p, game);
                }
            }, 1);
        } else {
            builder.addButton(new GUIButton("Region", Material.WOODEN_AXE, "Region: ", "Not set") {
                @Override
                public void onLeftClick(Player p) {
                    new DgGameConfig().show(p, game);
                }
            }, 1);
        }
        builder.addButton(new GUIButton("Current Icon", game.getIcon(),
        "Icon: " + game.getIcon().name()
        ), 0);
        // Add panes
        builder.addButton(new GUIButton(), 9, 10, 11, 12, 14, 15, 16, 17);
        // Return to game list
        builder.addButton(new GUIButton("Return", Material.MAGENTA_GLAZED_TERRACOTTA, "Return to game") {
            @Override
            public void onLeftClick(Player p) {
                new DgGameConfig().show(p, game);
            }
        }, 13);
        return builder.build(p);
    }

}
