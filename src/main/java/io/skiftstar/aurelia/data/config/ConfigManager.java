package io.skiftstar.aurelia.data.config;

import io.skiftstar.aurelia.Aurelia;
import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

  private File configFile;
  private YamlConfiguration config;
  private static final String CONFIG_DIRECTORY = "config";

  public ConfigManager(String fileName) {
    File configDirectory = new File(
      Aurelia.getInstance().getDataFolder(),
      CONFIG_DIRECTORY
    );
    if (!configDirectory.exists()) {
      configDirectory.mkdirs();
    }

    this.configFile = new File(configDirectory, fileName);

    if (!configFile.exists()) {
      try {
        configFile.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    this.config = YamlConfiguration.loadConfiguration(configFile);
  }

  public YamlConfiguration getConfig() {
    return this.config;
  }

  public void saveConfig() {
    try {
      this.config.save(configFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getInt(String key, int defaultValue) {
    return this.config.getInt(key, defaultValue);
  }

  public String getString(String key, String defaultValue) {
    return this.config.getString(key, defaultValue);
  }

  public Location getLocation(String key, Location defaultValue) {
    final int x = this.config.getInt(key + ".x", defaultValue.getBlockX());
    final int y = this.config.getInt(key + ".y", defaultValue.getBlockY());
    final int z = this.config.getInt(key + ".z", defaultValue.getBlockZ());
    final String worldName = this.config.getString(key + ".world", defaultValue.getWorld().getName());
    final float yaw = (float) this.config.getDouble(key + ".yaw", defaultValue.getYaw());
    final float pitch = (float) this.config.getDouble(key + ".pitch", defaultValue.getPitch());
    return new Location(Aurelia.getInstance().getServer().getWorld(worldName), x, y, z, yaw, pitch);
  }
}
