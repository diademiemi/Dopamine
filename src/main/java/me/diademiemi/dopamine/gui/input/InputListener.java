package me.diademiemi.dopamine.gui.input;

import me.diademiemi.dopamine.Dopamine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class InputListener implements Listener {
    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        if (Input.getInputs().containsKey(e.getPlayer())) {
            Dopamine.getPlugin().getServer().getScheduler()
                .runTask(Dopamine.getPlugin(), Runnable -> {
                    Input.getInputs().get(e.getPlayer()).onInput(e.getMessage());
                });
            e.setCancelled(true);
        }
    }

}
