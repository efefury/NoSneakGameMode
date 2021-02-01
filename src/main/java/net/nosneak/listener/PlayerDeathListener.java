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
    private int timer;

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
            Bukkit.broadcastMessage(Utils.colourize("&cThe player &a" + plugin.getPlayersInGame().get(0).getName() + "&c won the game!"));
            timer();
            plugin.setDone(true);
            plugin.setStarted(false);

        }
        event.setCancelled(true);
    }

    private void timer() {
        timer = 5;
        Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> {
            timer--;
            sendEndMessage( timer);
            if(timer == 0)
                bukkitTask.cancel();
        }, 0, 20);
    }

    private void sendEndMessage( int time) {
        if(time == 0) {
            Bukkit.broadcastMessage(Utils.colourize("&aThe server stops &anow"));
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.kickPlayer("The server stopped, thanks for playing!");
                all.getServer().shutdown();
            }
            return;
        }
        for(Player all : Bukkit.getOnlinePlayers())
            all.sendActionBar(Utils.colourize("&aThe game stops in &a" + time + " seconds"));
        Bukkit.broadcastMessage(Utils.colourize("&aThe game stops in &a" + time + " seconds"));
    }
}
