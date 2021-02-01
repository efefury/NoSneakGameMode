package net.nosneak.commands;

import net.nosneak.NoSneakPlugin;
import net.nosneak.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StopGame implements CommandExecutor {

    private final NoSneakPlugin plugin;

    public StopGame(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        plugin.cancelGame();
        player.sendMessage(Utils.colourize("&cYou cancelled the game, please set the locations for the game if you didn't yet."));
        return true;
    }
}
