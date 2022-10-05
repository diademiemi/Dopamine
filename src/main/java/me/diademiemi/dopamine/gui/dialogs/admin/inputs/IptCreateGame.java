package me.diademiemi.dopamine.gui.dialogs.admin.inputs;

import org.bukkit.entity.Player;

import me.diademiemi.dopamine.gui.dialogs.game.admin.DgConfirmCreateGame;
import me.diademiemi.dopamine.gui.input.Input;

public class IptCreateGame extends Input {

    public IptCreateGame(Player player) {
        super(player, "Please enter the name of the game you want to create:");
    }

    public void action(String input) {
        new DgConfirmCreateGame().show(player, input);
    }
}
