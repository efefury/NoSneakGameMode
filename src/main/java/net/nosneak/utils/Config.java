package net.nosneak.utils;

import net.nosneak.NoSneakPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

import java.io.File;
import java.io.IOException;

public class Config {

    private final NoSneakPlugin plugin;
    private final File file;
    private final FileConfiguration config;

    public Config(NoSneakPlugin plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "/locations.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Location getLocation(String arg) {
        int x = config.getInt("locations. " + arg + ".X");
        int y = config.getInt("locations. " + arg + ".Y");
        int z = config.getInt("locations. " + arg + ".Z");
        String worldName = config.getString("locations. " + arg + ".World");
        double yaw = config.getDouble("locations. " + arg + ".Yaw");
        double pitch = config.getDouble("locations. " + arg + ".Pitch");
        if(worldName == null) return null;
        World world = Bukkit.getWorld(worldName);
        if(world == null) return null;
        return new Location(world, x,y,z,(float)yaw, (float)pitch);
    }

}
