package net.nosneak.utils;

import org.bukkit.ChatColor;

public class Utils {

    public final static String colourize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
