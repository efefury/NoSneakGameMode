package net.nosneak.listener;

import net.nosneak.NoSneakPlugin;
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
        if (!plugin.isStarted()) return;
        Player player = event.getPlayer();
        player.getLocation().subtract(0, -1, 0).getBlock().setType(Material.AIR);

    }
}
