package net.nosneak.utils;

import net.nosneak.NoSneakPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardAPI {

    private final NoSneakPlugin plugin;

    public ScoreboardAPI(NoSneakPlugin plugin) {
        this.plugin = plugin;
    }

    public void updateScoreboard() {
        if(!plugin.isStarted() && plugin.isDone()) return;
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("abcde", "abcde");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Utils.colourize("&6No Sneaking allowed!"));
        objective.getScore(Utils.colourize("&7Player: " + plugin.getPlayersInGame().size() + "/§6" + NoSneakPlugin.MAX_PLAYERS)).setScore(4);
        objective.getScore(" ").setScore(3);
        if (plugin.getPlayersInGame().size() >= NoSneakPlugin.MIN_PLAYERS) {
            objective.getScore(Utils.colourize("&7Until start:")).setScore(2);
            objective.getScore(Utils.colourize("&a" + plugin.getTime() + " seconds!")).setScore(1);
        } else {
            objective.getScore(Utils.colourize("&7Until start:")).setScore(2);
            objective.getScore(Utils.colourize("&aWaiting for players")).setScore(1);
        }
        for (Player current : Bukkit.getOnlinePlayers())
            current.setScoreboard(scoreboard);
    }
}


