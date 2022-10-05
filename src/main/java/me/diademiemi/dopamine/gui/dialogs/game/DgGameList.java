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

        for (int i : GameList.getGameOrder().keySet()) {

            if (i >= 27 + (page * 27)) {
                break;
            }

            Game game = GameList.getGame(i);

            if (game == null) {
                break;
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
                }, i);
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
        builder.addButton(new GUIButton("Previous page", Material.RED_STAINED_GLASS_PANE) {
            @Override
            public void onLeftClick(Player p) {
                if (page > 0) {
                    show(p, page - 1);
                }
            }
        }, 28);
        builder.addButton(new GUIButton("Return to main menu", Material.BARRIER) {
            @Override
            public void onLeftClick(Player p) {
                close(p);
                new DgMainAdmin().show(p);
            }
        }, 31);
        builder.addButton(new GUIButton("Next page", Material.LIME_STAINED_GLASS_PANE) {
            @Override
            public void onLeftClick(Player p) {
                if (page < GameList.getGames().size() / 27) {
                    show(p, page + 1);
                }
            }
        }, 34);
        builder.addButton(new GUIButton(), 27, 29, 30, 32, 33, 35);


        return builder.build(player);
    }
}
