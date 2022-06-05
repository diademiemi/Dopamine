package me.diademiemi.dopamine.gui.menu;

import org.bukkit.entity.Player;

import me.diademiemi.dopamine.gui.GUIButton;

import java.util.HashMap;

public class MenuBuilder {
    private String title;
    private MenuSize size;
    public HashMap<Integer, GUIButton> buttons;

    public MenuBuilder(String title) {
        this.title = title;
        this.buttons = new HashMap<Integer, GUIButton>();
    }

    public MenuBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MenuBuilder setSize(MenuSize size) {
        this.size = size;
        return this;
    }

    public MenuBuilder addButton(int slot, GUIButton button) {
        this.buttons.put(slot, button);
        return this;
    }

    public Menu build(Player player) {
        return new Menu(player, size, title, buttons);
    }

}
