package me.diademiemi.dopamine.gui.dialogs.game;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.game.GameList;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.admin.DgMainAdmin;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.dialogs.game.admin.DgGameConfig;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DgGameList implements Dialog {
    @Override
    public Menu create(Player player, Object... args) {
        int page = (int) args[0];
        MenuBuilder builder = new MenuBuilder("Game List");
        builder.setSize(MenuSize.FOUR_ROWS);

        boolean hasPrev = page > 0;
        boolean hasNext = false;

        for (int i = (page + 1) * 27; i < ((page + 2) * 27); i++) {
            if (GameList.getGame(i) != null) {
                hasNext = true;
                break;
            }
        }

        for (int i : GameList.getGameOrder().keySet()) {

            // Break if outside of page range
            if (i < page * 27 || i >= (page + 1) * 27) {
                continue;
            }

            Game game = GameList.getGame(i);

            if (game == null) {
                continue;
            }


            if (player.hasPermission("dopamine.config.games")) {
                builder.addButton( new GUIButton(game.getName(), game.getIcon(), "Right click to configure this game") {

                    @Override
                    public void onLeftClick(Player p) {
                        close(p);
                        p.teleport(game.getWarp());
                    }
                    @Override
                    public void onRightClick(Player p) {
                        new DgGameConfig().show(p, game);
                    }
                }, i - (page * 27));
            } else {
                builder.addButton( new GUIButton(game.getName(), game.getIcon()) {

                    @Override
                    public void onLeftClick(Player p) {
                        close(p);
                        p.teleport(game.getWarp());
                    }
                }, i);
            }

            i++;
        }
        if (hasPrev) {
            builder.addButton(new GUIButton("Previous page", Material.RED_STAINED_GLASS_PANE) {
                @Override
                public void onLeftClick(Player p) {
                    show(p, page - 1);
                }
            }, 28);
        } else {
            builder.addButton(new GUIButton(), 28);
        }

        builder.addButton(new GUIButton("Return to main menu", Material.BARRIER) {
            @Override
            public void onLeftClick(Player p) {
                close(p);
                new DgMainAdmin().show(p);
            }
        }, 31);

        if (hasNext) {
            builder.addButton(new GUIButton("Next page", Material.GREEN_STAINED_GLASS_PANE) {
                @Override
                public void onLeftClick(Player p) {
                    show(p, page + 1);
                }
            }, 34);
        } else {
            // blank
            builder.addButton(new GUIButton(), 34);
        }

        builder.addButton(new GUIButton(), 27, 29, 30, 32, 33, 35);


        return builder.build(player);
    }
}
