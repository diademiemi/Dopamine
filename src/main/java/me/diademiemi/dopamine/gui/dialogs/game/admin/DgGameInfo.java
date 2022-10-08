package me.diademiemi.dopamine.gui.dialogs.game.admin;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import me.diademiemi.dopamine.lang.Button;
import me.diademiemi.dopamine.lang.Message;
import me.diademiemi.dopamine.lang.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class DgGameInfo implements Dialog {
    @Override
    public Menu create(Player p, Object... args) {
        Game game = (Game) args[0];
        MenuBuilder builder = new MenuBuilder(Title.get("confirmation"));
        builder.setSize(MenuSize.TWO_ROWS);
        Region region = game.getRegion();
        if (region != null) {
                builder.addButton(new GUIButton(Material.GRASS_BLOCK,
                        Button.get("describe-region",
                                "x1", String.valueOf(region.getMinimumPoint().getX()),
                                "y1", String.valueOf(region.getMinimumPoint().getY()),
                                "z1", String.valueOf(region.getMinimumPoint().getZ()),
                                "x2", String.valueOf(region.getMaximumPoint().getX()),
                                "y2", String.valueOf(region.getMaximumPoint().getY()),
                                "z2", String.valueOf(region.getMaximumPoint().getZ()))
                        ) {
                @Override
                public void onLeftClick(Player p) {
                    try {
                        com.sk89q.worldedit.entity.Player wePlayer = com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(p);
                        LocalSession session = WorldEdit.getInstance().getSessionManager().get(wePlayer);
                        session.setRegionSelector(region.getWorld(), new CuboidRegionSelector(region.getWorld(), region.getMinimumPoint(), region.getMaximumPoint()));
                    } catch (Exception e) {
                        Message.send(p, "err-setting-player-sel");
                    }
                }
            }, 1);
        } else {
            builder.addButton(new GUIButton(Material.WOODEN_AXE, Button.get("describe-no-region")) {
                @Override
                public void onLeftClick(Player p) {
                    new DgGameConfig().show(p, game);
                }
            }, 1);
        }
        builder.addButton(new GUIButton(game.getIcon(), Button.get("describe-icon", "icon", game.getIcon().name())
        ), 0);
        // Add panes
        builder.addButton(new GUIButton(), 9, 10, 11, 12, 14, 15, 16, 17);
        // Return to game list
        builder.addButton(new GUIButton(Material.MAGENTA_GLAZED_TERRACOTTA, Button.get("return-to-game-config")) {
            @Override
            public void onLeftClick(Player p) {
                new DgGameConfig().show(p, game);
            }
        }, 13);
        return builder.build(p);
    }

}
