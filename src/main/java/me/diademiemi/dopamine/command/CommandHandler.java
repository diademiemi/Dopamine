package me.diademiemi.dopamine.command;

import me.diademiemi.dopamine.config.Config;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import me.diademiemi.dopamine.gui.dialogs.MainAdminDialog;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                MainAdminDialog.showDialog((Player) sender);
            } else if (args.length == 1) {
                switch (args[0]) {
                    case "save":
                        Config.writeConfig();
                        sender.sendMessage("Saved!");
                        break;
                    default:
                        sender.sendMessage("Unknown command. Type /dopamine help for help");
                        break;
                }
            }
        }
        return true;
    }

}
