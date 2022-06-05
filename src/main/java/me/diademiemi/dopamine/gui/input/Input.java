package me.diademiemi.dopamine.gui.input;

import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class Input {
    public static HashMap<Player, Input> inputs = new HashMap<Player, Input>();
    public Player player;
    public String preamble;

    public Input(Player player) {
        this.player = player;
        addInput(this);
    }

    public Input(Player player, String preamble) {
        this.player = player;
        player.sendMessage(preamble);
        addInput(this);
    }


    public static HashMap<Player, Input> getInputs() {
        return inputs;
    }

    public void addInput(Input input) {
        inputs.put(input.getPlayer(), input);
    }

    public void remove() {
        inputs.remove(this.getPlayer());
    }

    public Player getPlayer() {
        return player;
    }


    public void onInput(String input) {
        inputs.remove(player);
        action(input);
    }

    public abstract void action(String input);

}
