package net.nosneak;

import net.nosneak.commands.SetupCommand;
import net.nosneak.commands.StartCommand;
import net.nosneak.commands.StopGame;
import net.nosneak.listener.*;
import net.nosneak.utils.Config;
import net.nosneak.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.List;

public final class NoSneakPlugin extends JavaPlugin {

    private final List<Player> players = new ArrayList<>();
    public static final int MAX_PLAYERS = 8;
    public static final int MIN_PLAYERS = 2;
    private boolean isStarted = false;
    private boolean isDone = false;
    private Config config;
    private int startTime = 30;
    private int endTime = 5;
    private boolean isGoing = false;

    @Override
    public void onEnable() {
        config = new Config(this);
        register();
    }

    private void register() {
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerHandleConnection(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSneakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ProtectionListener(this), this);
        getCommand("setup").setExecutor(new SetupCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("stopgame").setExecutor(new StopGame(this));

        if(getConfiguration().getConfig().get("locations." ) == null)
        getLogger().warning("Please set the locations with /stopgame and then set the locations with /setup .");

        Bukkit.getScheduler().runTaskTimer(this, bukkitTask -> {
            if(isGoing && !isDone)
            for(Player all : Bukkit.getOnlinePlayers())
                all.sendActionBar(Utils.colourize("&aPlayers alive: " + getPlayersInGame().size()));


        }, 0, 5);

        if(getConfiguration().getConfig().getString("start-permission") == null) {
            getConfiguration().getConfig().set("start-permission", "ur permission");
        }
        if(getConfiguration().getConfig().getString("setup-permission") == null) {
            getConfiguration().getConfig().set("setup-permission", "ur permission");
        }
        if(getConfiguration().getConfig().getString("no-perms-message") == null) {
            getConfiguration().getConfig().set("no-perms-message", "&cYou are not allowed to use this command!");
        }
    }

    @Override
    public void onDisable() {
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

    public void startTimer() {
        Bukkit.getScheduler().runTaskTimer(this, bukkitTask -> {
            startTime--;
            if(endTime > 1000) return;
            for (Player all : Bukkit.getOnlinePlayers())
                all.setLevel(startTime);
            sendStartMessage(startTime);
            if (startTime == 0) {
                bukkitTask.cancel();
                isGoing = true;
                isStarted = true;
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.teleport(this.getConfiguration().getLocation("start"));
                    all.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                }
            }
        }, 0, 20);
    }

    private void sendStartMessage(int time) {
        for (Player all : Bukkit.getOnlinePlayers())
            all.sendActionBar(Utils.colourize("&aThe game starts in &6" + time + "&a seconds"));
        Bukkit.broadcastMessage(Utils.colourize("&aThe game starts in &6" + time + "&a seconds"));
    }

    public void startEndTimer() {
        endTime = 5;
        Bukkit.getScheduler().runTaskTimer(this, bukkitTask -> {
            endTime--;
            sendEndMessage(endTime);
            if (endTime == 0)
                bukkitTask.cancel();
        }, 0, 20);
    }

    private void sendEndMessage(int time) {
        if (time == 0) {
            Bukkit.broadcastMessage(Utils.colourize("&aThe server stops &anow"));
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.kickPlayer("The server stopped, thanks for playing!");
                all.getServer().shutdown();
            }
            return;
        }
        for (Player all : Bukkit.getOnlinePlayers())
            all.sendActionBar(Utils.colourize("&aThe game stops in &a" + endTime + " seconds"));
        Bukkit.broadcastMessage(Utils.colourize("&aThe game stops in &a" + endTime + " seconds"));
    }

    public int getTime() {
        return startTime;
    }

    public void setTime(int time) {
        this.startTime = time;
    }

    public boolean isGoing() {
        return isGoing;
    }

    public void cancelGame() {
        startTime = 10000;
    }
}
