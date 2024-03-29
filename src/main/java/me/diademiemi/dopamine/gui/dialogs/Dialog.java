package me.diademiemi.dopamine.gui.dialogs;

import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.menu.Menu;
import org.bukkit.entity.Player;

public interface Dialog {

    default void show(Player player, Object... args) {
        try {
            GUI.getGUI(player).close();
        } catch (NullPointerException e) {
            // Do nothing
        }
        create(player, args).open();
    }

    default void close(Player player) {
        GUI.getGUI(player).close();
    }

    Menu create(Player p, Object... args);

}
