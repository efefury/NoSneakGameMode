package net.nosneak.listener;

import net.nosneak.NoSneakPlugin;
import net.nosneak.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final NoSneakPlugin plugin;

    public PlayerMoveListener(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!plugin.isStarted()) {
            return;
        }
        Player player = event.getPlayer();
        if(!plugin.getPlayersInGame().contains(player)) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> player.getLocation().add(0, -1, 0).getBlock().setType(Material.AIR),30);
        if(!plugin.getPlayersInGame().contains(player))  return;
        if(player.getLocation().getY() <= plugin.getConfiguration().getLocation("start").getY() - 15){
            Bukkit.broadcastMessage(Utils.colourize("&aThe player &c" + player.getName() + "&a died!"));
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            plugin.getPlayersInGame().remove(player);


            if (plugin.getPlayersInGame().size() == 1) {
                Bukkit.broadcastMessage(Utils.colourize("&cThe player &a" + plugin.getPlayersInGame().get(0).getName() + "&c won the game!"));
                plugin.startEndTimer();
                plugin.setDone(true);
                plugin.setStarted(false);
                plugin.getPlayersInGame().remove(player);
                for(Player all : Bukkit.getOnlinePlayers())
                player.teleport(all.getLocation());
            }
        }
    }
}
