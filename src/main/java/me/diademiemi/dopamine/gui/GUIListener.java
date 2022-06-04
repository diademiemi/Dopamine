package me.diademiemi.dopamine.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        GUI gui = GUI.getGUI(e.getInventory(), player);
        if (gui != null) {
            gui.onClick(e);
            e.setCancelled(true);
        }
    }
}
