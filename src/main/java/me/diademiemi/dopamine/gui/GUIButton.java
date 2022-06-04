package me.diademiemi.dopamine.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUIButton {
    private ItemStack stack;

    public GUIButton(String name, Material material, int amount, String... lore) {
        stack = new ItemStack(material, amount);

        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lore));

        stack.setItemMeta(itemMeta);
    }

    public void updateAmount(int amount) {
        stack.setAmount(amount);
    }

    public void updateName(String string) {
        stack.getItemMeta().setDisplayName(string);
    }

    public void updateLore(String... lore) {
        stack.getItemMeta().setLore(Arrays.asList(lore));
    }

    public ItemStack getStack() {
        return stack;
    }

}
