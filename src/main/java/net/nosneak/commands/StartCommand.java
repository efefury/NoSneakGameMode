package net.nosneak.commands;

import net.nosneak.NoSneakPlugin;
import net.nosneak.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {

    private final NoSneakPlugin plugin;

    public StartCommand(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You need to be ingame to use this game!");
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission(plugin.getConfiguration().getConfig().getString("start-permission"))){
        player.sendMessage(Utils.colourize(plugin.getConfiguration().getConfig().getString("no-perms-message")));
        return true;
    }
         if(plugin.getPlayersInGame().size() < NoSneakPlugin.MIN_PLAYERS) {
             player.sendMessage(Utils.colourize("&cThere aren't enough players to start the game!"));
            return true;
        }
         if(plugin.isStarted() || plugin.isDone()) {
             player.sendMessage(Utils.colourize("&CThe game has already started!"));
             return true;
         }

         if(plugin.getTime() < 5)  {
             player.sendMessage(Utils.colourize("&cThe time is already under 5 seconds!"));
             return true;
         }
         plugin.setTime(5);
        Bukkit.broadcastMessage(Utils.colourize("&cThe game got force-started by &a" + player.getName()));
        return true;
    }
}
