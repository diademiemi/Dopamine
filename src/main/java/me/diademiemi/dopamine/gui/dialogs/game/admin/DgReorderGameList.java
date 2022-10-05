package me.diademiemi.dopamine.gui.dialogs.game.admin;

import me.diademiemi.dopamine.game.GameList;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;
import me.diademiemi.dopamine.gui.dialogs.admin.DgMainAdmin;
import me.diademiemi.dopamine.gui.dialogs.Dialog;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuBuilder;
import me.diademiemi.dopamine.gui.menu.MenuSize;
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

        MenuBuilder builder = new MenuBuilder("List of games");
        builder.setSize(MenuSize.FOUR_ROWS);

        for (int i = 0; i < 27 + (page * 27); i++) {

            String g = uncommittedChanges.get(i);

            if (g != null) {
                builder.addButton( new GUIButton(GameList.getGame(g).getName(), GameList.getGame(g).getIcon(), "Click to select this game", "Then click the slot you want to swap it with") {

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
                }, i);
            } else {
                if (playerSelection.containsKey(p.getUniqueId())) {
                    int finalI = i;
                    builder.addButton (new GUIButton("Move to empty slot", Material.GRAY_STAINED_GLASS_PANE) {
                        @Override
                        public void onLeftClick(Player p) {
                            String oldSlot = playerSelection.get(p.getUniqueId());
                            uncommittedChanges.remove(GameList.getGameIndex(oldSlot, uncommittedChanges));
                            uncommittedChanges.put(finalI, oldSlot);
                            changed = true;
                            playerSelection.remove(p.getUniqueId());
                            show(p, page);
                        }
                    }, i);
                }
            }
        }

        builder.addButton(new GUIButton("Previous page", Material.RED_STAINED_GLASS_PANE) {
            @Override
            public void onLeftClick(Player p) {
                if (page > 0) {
                    show(p, page - 1);
                }
            }
        }, 28);

        if (playerSelection.containsKey(p.getUniqueId())) {
            builder.addButton(new GUIButton(GameList.getGame(playerSelection.get(p.getUniqueId())).getName(), GameList.getGame(playerSelection.get(p.getUniqueId())).getIcon(), "Current selection", "Click to unselect") {
                @Override
                public void onLeftClick(Player p) {
                    playerSelection.remove(p.getUniqueId());
                    show(p, page);
                }
            }, 30);
        } else if (changed) {
            builder.addButton(new GUIButton("Discard changes", Material.TNT) {
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

        builder.addButton(new GUIButton("Return to main menu", Material.BARRIER) {
            @Override
            public void onLeftClick(Player p) {
                new DgMainAdmin().show(p);
            }
        }, 31);

        if (!playerSelection.containsKey(p.getUniqueId()) && changed) {
            builder.addButton(new GUIButton("Commit changes", Material.FIREWORK_ROCKET) {
                @Override
                public void onLeftClick(Player p) {
                    playerSelection.clear();
                    GameList.setGameOrder(uncommittedChanges);
                    p.sendMessage("Changes committed");
                    changed = false;
                    show(p, page);
                }
            }, 32);
        } else {
            // blank
            builder.addButton(new GUIButton(), 32);
        }

        builder.addButton(new GUIButton("Next page", Material.LIME_STAINED_GLASS_PANE) {
            @Override
            public void onLeftClick(Player p) {
                if (page < GameList.getGames().size() / 27) {
                    show(p, page + 1);
                }
            }
        }, 34);
        builder.addButton(new GUIButton(), 27, 29, 33, 35);

        return builder.build(p);
    }
}
