package me.diademiemi.dopamine.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import me.diademiemi.dopamine.gui.dialogs.MainDialog;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            MainDialog.showDialog((Player) sender);
        }
        return true;
    }

}
