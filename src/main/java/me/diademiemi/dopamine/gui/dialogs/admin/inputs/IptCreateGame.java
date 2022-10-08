package me.diademiemi.dopamine.gui.dialogs.admin.inputs;

import me.diademiemi.dopamine.gui.dialogs.game.admin.DgConfirmCreateGame;
import me.diademiemi.dopamine.gui.input.Input;
import org.bukkit.entity.Player;

public class IptCreateGame extends Input {

    public IptCreateGame(Player player) {
        super(player, "input-create-game-name");
    }

    public void action(String input) {
        new DgConfirmCreateGame().show(player, input);
    }
}
