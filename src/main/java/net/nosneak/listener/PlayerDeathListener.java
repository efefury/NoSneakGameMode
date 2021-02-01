package net.nosneak.listener;

import net.nosneak.NoSneakPlugin;
import net.nosneak.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final NoSneakPlugin plugin;

    public PlayerDeathListener(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(!plugin.isStarted())  return;
        Player player = event.getEntity();
        plugin.getPlayersInGame().remove(player);
        Bukkit.broadcastMessage(Utils.colourize("&aThe player &c" + player.getName() + "&a died!"));
        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().clear();

        if (plugin.getPlayersInGame().size() == 1) {
            Bukkit.broadcastMessage("&cThe player &a" + plugin.getPlayersInGame().get(0).getName() + "&c won the game!");
            timer(player);
            plugin.setDone(true);
            plugin.setStarted(false);
        }
        event.setCancelled(true);
    }

    private void timer(Player player) {
        int timer = 5;
        Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> {
            sendEndMessage(player, timer);
        }, 0, 20);
    }

    private void sendEndMessage(Player player, int time) {
        if(time == 0) {
            player.sendMessage(Utils.colourize("&aThe server stops &anow"));
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.kickPlayer("The server stopped, thanks for playing!");
            }
            return;
        }

        player.sendActionBar(Utils.colourize("&aThe game ends in &a" + time + " seconds"));
        player.sendMessage(Utils.colourize("&aThe game ends in &a "+ time + "seconds"));
    }
}
