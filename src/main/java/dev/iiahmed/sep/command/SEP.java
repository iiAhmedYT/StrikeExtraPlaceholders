package dev.iiahmed.sep.command;

import dev.iiahmed.sep.StrikeExtraPlaceholders;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SEP implements CommandExecutor {

    private final StrikeExtraPlaceholders instance = StrikeExtraPlaceholders.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("StrikePractice.staff")) return true;
        if(args.length == 0){
            sender.sendMessage(ChatColor
                    .translateAlternateColorCodes('&', "\n&bCommands" +
                            "\n\n&e/SEP reload\n"));
            return true;
        }
        String subcmd = args[0];
        if(subcmd.equalsIgnoreCase("reload")){
            instance.reloadSystem();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&aStrikeExtraPlaceholders reloaded successfully :D"));
        }
        return false;
    }

}
