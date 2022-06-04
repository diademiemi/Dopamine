package me.diademiemi.dopamine.gui;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class GUI {
    public static HashMap<Player, GUI> guiMap = new HashMap<Player, GUI>();
    public HashMap<Integer, GUIButton> buttons;
    public Inventory inventory;
    public Player player;
    public String title;

    /**
     * @param gui GUI to add
     */
    public void addGUI(GUI gui) {
        guiMap.put(player, gui);
    }

    /**
     * @param gui GUI to remove
     */
    public void removeGUI(GUI gui) {
        guiMap.remove(player, gui);
    }

    /**
     * @return all loaded GUIs
     */
    public static HashMap<Player, GUI> getGuiMap() {
        return guiMap;
    }

    /**
     * @param inventory
     * @param player
     * @return the GUI of the given inventory and player
     */
    public static GUI getGUI(Inventory inventory, Player player) {
        return guiMap.get(player);
    }

    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        player.sendMessage(e.toString());
        if (e.getCurrentItem() != null) {
            player.sendMessage(e.getCurrentItem().toString());
        }
    }

    public void open() {
        player.openInventory(inventory);
    }

    public void close() {
        player.closeInventory();
    }

    public void addButton(int slot, GUIButton button) {
        inventory.setItem(slot, button.getStack());
    }

}
