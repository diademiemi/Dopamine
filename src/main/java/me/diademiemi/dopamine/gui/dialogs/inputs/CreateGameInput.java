package me.diademiemi.dopamine.gui.dialogs.inputs;

import org.bukkit.entity.Player;

import me.diademiemi.dopamine.gui.dialogs.game.CreateGameDialog;
import me.diademiemi.dopamine.gui.input.Input;

public class CreateGameInput extends Input {

    public CreateGameInput(Player player) {
        super(player, "Please enter the name of the game you want to create:");
    }

    public void action(String input) {
        CreateGameDialog.showDialog(player, input);
    }
}
