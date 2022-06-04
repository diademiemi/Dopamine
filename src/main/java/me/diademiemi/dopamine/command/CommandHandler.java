package me.diademiemi.dopamine.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import me.diademiemi.dopamine.Dopamine;
import me.diademiemi.dopamine.gui.GUI;
import me.diademiemi.dopamine.gui.menu.Menu;
import me.diademiemi.dopamine.gui.menu.MenuSize;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        System.out.println("TESTING");
        if (sender instanceof Player) {
            System.out.println("TESTING");
            Dopamine.getPlugin().getLogger().info("Opening GUI");
            Player player = (Player) sender;
            GUI gui = new Menu(MenuSize.FOUR_ROWS, "Testing", player);
            gui.open();
            Dopamine.getPlugin().getLogger().info("GUI opened");
        }
        return false;
    }

}
