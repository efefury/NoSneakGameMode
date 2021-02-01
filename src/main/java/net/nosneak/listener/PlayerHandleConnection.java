package net.nosneak.listener;

import net.nosneak.NoSneakPlugin;
import net.nosneak.utils.ScoreboardAPI;
import net.nosneak.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerHandleConnection implements Listener {

    private final NoSneakPlugin plugin;

    public PlayerHandleConnection(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        event.setJoinMessage(Utils.colourize("&c>> &6" + player.getName() + "&7 [" + (plugin.getPlayersInGame().size()+1) + "/" + NoSneakPlugin.MAX_PLAYERS) + "]");
        if (plugin.isStarted()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(Utils.colourize("&aThe game has already &cstarted&a so you are a spectator!"));
            return;
        }
        ScoreboardAPI scoreboardAPI = new ScoreboardAPI(plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, scoreboardAPI::updateScoreboard, 0,20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.setLevel(0);
        plugin.getPlayersInGame().add(player);
        player.teleport(plugin.getConfiguration().getLocation("spawn"));
        if (plugin.getPlayersInGame().size() == NoSneakPlugin.MIN_PLAYERS) {
            plugin.startTimer();

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayersInGame().remove(player);
        event.setQuitMessage(Utils.colourize("&c<< &6" + player.getName() + "&7[" +(plugin.getPlayersInGame().size()) + "/" + NoSneakPlugin.MAX_PLAYERS) + "]");
    }


}
