package net.nosneak.listener;

import net.nosneak.NoSneakPlugin;
import net.nosneak.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerHandleConnection implements Listener {

    private final NoSneakPlugin plugin;
    private int time;


    public PlayerHandleConnection(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        if (plugin.isStarted()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(Utils.colourize("&aThe game has already &cstarted&a so you are a spectator!"));
            return;
        }
        plugin.getPlayersInGame().add(player);
        event.setJoinMessage(Utils.colourize("&c>> &6" + player.getName() + "&7 [" + plugin.getPlayersInGame().size() + "/" + NoSneakPlugin.MAX_PLAYERS));
        player.teleport(plugin.getConfiguration().getLocation("spawn"));
        if (plugin.getPlayersInGame().size() == NoSneakPlugin.MIN_PLAYERS) {
            timer(player);

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayersInGame().remove(player);
        event.setQuitMessage(Utils.colourize("&c<< &6" + player.getName() + "&7[" + plugin.getPlayersInGame().size() + "/" + NoSneakPlugin.MAX_PLAYERS));
    }

    private void timer(Player player) {
        Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> {
            time--;
            sendStartMessage(player, time);
            if (time == 0) {
                bukkitTask.cancel();
                plugin.setStarted(true);
                player.teleport(plugin.getConfiguration().getLocation("start"));

            }
        }, 0, 20);
    }

    private void sendStartMessage(Player player, int time) {
        player.sendActionBar(Utils.colourize("&aThe game starts in &a" + time + " seconds"));
        player.sendMessage(Utils.colourize("&aThe game starts in &a" + time + " seconds"));
    }
}
