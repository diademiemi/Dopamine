package me.diademiemi.dopamine.command;

import me.diademiemi.dopamine.gui.dialogs.admin.DgMainAdmin;
import me.diademiemi.dopamine.gui.dialogs.game.DgGameList;
import me.diademiemi.dopamine.lang.Message;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("dopamine")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("dopamine.admin")) {
                        new DgMainAdmin().show((Player) sender);
                    } else if (sender.hasPermission("dopamine.list")){
                        new DgGameList().show((Player) sender, 0);
                    } else {
                        Message.send(sender, "no-permission");
                    }
                    new DgMainAdmin().show((Player) sender);
                } else {
                    Message.send(sender, "no-console");
                }
            } else if (args.length == 1) {
                switch (args[0]) {
                    default:
                        Message.send(sender, "unknown-arguments", "label", label);
                        break;
                }
            }
        } else if (label.equalsIgnoreCase("games")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("dopamine.list")) {
                    new DgGameList().show((Player) sender, 0);
                } else {
                    Message.send(sender, "no-permission");
                }
            } else {
                Message.send(sender, "no-console");
            }
        }
        return true;
    }

}
