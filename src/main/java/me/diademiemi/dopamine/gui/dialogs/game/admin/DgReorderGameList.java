package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.Game;
import me.diademiemi.dopamine.game.GameList;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.dialogs.admin.DgMainAdmin;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
import me.diademiemi.dopamine.lang.Button;
import me.diademiemi.dopamine.lang.Message;
import me.diademiemi.dopamine.lang.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class DgReorderGameList implements Dialog {

    private static Boolean changed = false;

    private static HashMap<Integer, String> uncommittedChanges;

    private static HashMap<UUID, String> playerSelection = new HashMap<UUID, String>();

    @Override
    public Menu create(Player p, Object... args) {
        int page = (int) args[0];


        if (uncommittedChanges == null) {
            uncommittedChanges = (HashMap<Integer, String>) GameList.getGameOrder().clone();
        }

        MenuBuilder builder = new MenuBuilder(Title.get("game-list-reorder"));
        builder.setSize(MenuSize.FOUR_ROWS);

        boolean hasPrev = page > 0;
        boolean pageIsEmpty = true;

        for (int i = page * 27; i < (page + 1) * 27; i++) {
            if (GameList.getGame(i) != null) {
                pageIsEmpty = false;
                break;
            }
        }

        for (int i = page * 27; i < 27 + (page * 27); i++) {

            String g = uncommittedChanges.get(i);

            if (g != null) {
                builder.addButton( new GUIButton(GameList.getGame(g).getIcon(), Button.get("game-reorder-list-game", "name", GameList.getGame(g).getName())) {

                    @Override
                    public void onLeftClick(Player p) {
                        if (playerSelection.containsKey(p.getUniqueId())) {
                            if (playerSelection.get(p.getUniqueId()).equals(g)) {
                                // Unselect
                                playerSelection.remove(p.getUniqueId());
                            } else {
                                // Swap spaces
                                String oldSlot = playerSelection.get(p.getUniqueId());
                                int oldSlotIndex = GameList.getGameIndex(oldSlot, uncommittedChanges);
                                int newSlotIndex = GameList.getGameIndex(g, uncommittedChanges);
                                uncommittedChanges.put(oldSlotIndex, g);
                                uncommittedChanges.put(newSlotIndex, oldSlot);
                                changed = true;
                                playerSelection.remove(p.getUniqueId());
                            }
                        } else {
                            // Select
                            playerSelection.put(p.getUniqueId(), g);
                        }
                        show(p, page);
                    }
                }, i - (page * 27));
            } else {
                if (playerSelection.containsKey(p.getUniqueId())) {
                    int finalI = i;
                    builder.addButton (new GUIButton(Material.GRAY_STAINED_GLASS_PANE, Button.get("game-reorder-list-move-empty")) {
                        @Override
                        public void onLeftClick(Player p) {
                            String oldSlot = playerSelection.get(p.getUniqueId());
                            uncommittedChanges.remove(GameList.getGameIndex(oldSlot, uncommittedChanges));
                            uncommittedChanges.put(finalI, oldSlot);
                            changed = true;
                            playerSelection.remove(p.getUniqueId());
                            show(p, page);
                        }
                    }, i - (page * 27));
                }
            }
        }
        if (hasPrev) {
            builder.addButton(new GUIButton(Material.RED_STAINED_GLASS_PANE, Button.get("previous-page")) {
                @Override
                public void onLeftClick(Player p) {
                    show(p, page - 1);
                }
            }, 28);
        } else {
            builder.addButton(new GUIButton(), 28);
        }

        if (playerSelection.containsKey(p.getUniqueId())) {
            Game game = GameList.getGame(playerSelection.get(p.getUniqueId()));
            builder.addButton(new GUIButton(game.getIcon(), Button.get("game-reorder-list-current-selection", "name", game.getName())) {
                @Override
                public void onLeftClick(Player p) {
                    playerSelection.remove(p.getUniqueId());
                    show(p, page);
                }
            }, 30);
        } else if (changed) {
            builder.addButton(new GUIButton(Material.TNT, Button.get("discard-changes")) {
                @Override
                public void onLeftClick(Player p) {
                    playerSelection.clear();
                    uncommittedChanges = null;
                    changed = false;
                    show(p, page);
                }
            }, 30);
        } else {
            // blank
            builder.addButton(new GUIButton(), 30);
        }

        builder.addButton(new GUIButton(Material.BARRIER, Button.get("return-to-admin-menu")) {
            @Override
            public void onLeftClick(Player p) {
                new DgMainAdmin().show(p);
            }
        }, 31);

        if (!playerSelection.containsKey(p.getUniqueId()) && changed) {
            builder.addButton(new GUIButton(Material.FIREWORK_ROCKET, Button.get("commit-changes")) {
                @Override
                public void onLeftClick(Player p) {
                    playerSelection.clear();
                    GameList.setGameOrder(uncommittedChanges);
                    Message.send(p, "changes-committed");
                    changed = false;
                    show(p, page);
                }
            }, 32);
        } else {
            // blank
            builder.addButton(new GUIButton(), 32);
        }

        if (!pageIsEmpty) {
            builder.addButton(new GUIButton(Material.GREEN_STAINED_GLASS_PANE, Button.get("next-page")) {
                @Override
                public void onLeftClick(Player p) {
                    show(p, page + 1);
                }
            }, 34);
        } else {
            // blank
            builder.addButton(new GUIButton(), 34);
        }

        builder.addButton(new GUIButton(), 27, 29, 33, 35);

        return builder.build(p);
    }
}
