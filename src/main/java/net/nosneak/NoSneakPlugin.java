package net.nosneak;

import net.nosneak.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class NoSneakPlugin extends JavaPlugin {

    private List<Player> players = new ArrayList<>();
    public static final int MAX_PLAYERS = 8;
    public static final int MIN_PLAYERS = 3;
    private boolean isStarted = false;
    private boolean isDone = false;
    private Config config;

    @Override
    public void onEnable() {
        config = new Config(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public List<Player> getPlayersInGame() {
        return players;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Config getConfiguration() {
        return config;
    }
}
