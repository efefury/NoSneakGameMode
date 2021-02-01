package net.nosneak.commands;

import net.nosneak.NoSneakPlugin;
import net.nosneak.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetupCommand implements CommandExecutor {

    private final NoSneakPlugin plugin;

    public SetupCommand(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,  @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            plugin.getLogger().severe("You are not a player!");
            return true;
        }
        Player player = (Player) sender;
        if(args.length == 0) {
            player.sendMessage(Utils.colourize("&cWrong Syntax: /setup <spawn, start>"));
            return true;
        }
        if(args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("start")) {
            player.sendMessage(Utils.colourize("&aSaved the locations for " + args[0]));
            plugin.getConfiguration().getConfig().set("locations." + args[0]+ ".X", player.getLocation().getX());
            plugin.getConfiguration().getConfig().set("locations." + args[0]+ ".Y", player.getLocation().getY());
            plugin.getConfiguration().getConfig().set("locations." + args[0]+ ".Z", player.getLocation().getZ());
            plugin.getConfiguration().getConfig().set("locations." + args[0]+ ".World", player.getLocation().getWorld().getName());
            plugin.getConfiguration().getConfig().set("locations." + args[0]+ ".Yaw", player.getLocation().getYaw());
            plugin.getConfiguration().getConfig().set("locations." + args[0]+ ".Pitch", player.getLocation().getPitch());
            plugin.getConfiguration().save();
        }
         return true;
    }
}
