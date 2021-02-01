package net.nosneak.listener;

import net.nosneak.NoSneakPlugin;
import net.nosneak.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerSneakListener implements Listener {

    private final NoSneakPlugin plugin;

    public PlayerSneakListener(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        if(!plugin.isStarted())  return;
        Player player = event.getPlayer();
        Bukkit.broadcastMessage(Utils.colourize("&aThe player &c" + player.getName() + "&a sneaked!"));
        player.damage(10000);
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH, 1f,1f);
        plugin.getPlayersInGame().remove(player);
        player.setGameMode(GameMode.SPECTATOR);
    }
}
