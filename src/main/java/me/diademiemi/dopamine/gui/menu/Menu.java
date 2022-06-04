package me.diademiemi.dopamine.gui.menu;

import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.GUIButton;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Menu extends GUI {
    private MenuSize size;

    /**
     * Create menu
     * @param size the size of the GUI
     * @param title the title of the GUI
     * @param player the Player for the GUI
     */
    public Menu(MenuSize size, String title, Player player) {
        this.inventory = Bukkit.getServer().createInventory(null, size.getSize(), title);
        this.size = size;
        this.title = title;
        this.player = player;
        this.buttons = new HashMap<Integer, GUIButton>();

        guiMap.put(player, this);
        addButton(1, new GUIButton("Button 1", Material.STONE, 16, "Testing something..."));
    }

    public void addButton(int slot, GUIButton button) {
        this.buttons.put(slot, button);
        inventory.setItem(slot, button.getStack());
    }


}
