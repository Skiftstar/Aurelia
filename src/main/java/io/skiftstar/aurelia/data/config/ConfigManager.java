package io.skiftstar.aurelia.data.config;

import io.skiftstar.aurelia.Aurelia;
import io.skiftstar.aurelia.util.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

public class ConfigManager extends YamlConfiguration {

  private File configFile;
  public static final String CONFIG_DIRECTORY = "config";
  public static final String PLAYER_DIRECTORY = "players";

  public ConfigManager(String folderName, String fileName) {
    File configDirectory = new File(
      Aurelia.getInstance().getDataFolder(),
      folderName
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
    try {
      super.load(configFile);
    } catch (FileNotFoundException e) {
      Logger.error(fileName + " not found");
      e.printStackTrace();
    } catch (IOException e) {
      Logger.error(fileName + " could not be loaded");
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      Logger.error(fileName + " is not a valid YAML file");
      e.printStackTrace();
    }
    Logger.log("Loaded " + configFile.getAbsolutePath());
  }

  public void save() {
    try {
      save(configFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get a location from the config
   * @param key The key to get the location from
   * @param defaultValue The default value to return if the key is not found
   * @return The location at the key, or the default value if the key is not found
   */
  public Location getLocation(String key, @NotNull Location defaultValue) {
    if (!super.contains(key)) {
      return defaultValue;
    }

    if (defaultValue == null) {
      defaultValue = new Location(
        Bukkit.getWorlds().get(0),
        0,
        0,
        0
      );
    }

    final int x = super.getInt(key + ".x", defaultValue.getBlockX());
    final int y = super.getInt(key + ".y", defaultValue.getBlockY());
    final int z = super.getInt(key + ".z", defaultValue.getBlockZ());
    final String worldName = super.getString(
      key + ".world",
      defaultValue.getWorld().getName()
    );
    final float yaw = (float) super.getDouble(
      key + ".yaw",
      defaultValue.getYaw()
    );
    final float pitch = (float) super.getDouble(
      key + ".pitch",
      defaultValue.getPitch()
    );
    return new Location(
      Bukkit.getWorld(worldName),
      x,
      y,
      z,
      yaw,
      pitch
    );
  }

  /**
   * Set a location in the config
   * @param key The key to set the location at
   * @param value The location to set. If null, the key will be removed
   */
  public void setLocation(String key, Location value) {
    if (value == null) {
      super.set(key, value);
      return;
    }

    super.set(key + ".x", value.getBlockX());
    super.set(key + ".y", value.getBlockY());
    super.set(key + ".z", value.getBlockZ());
    super.set(key + ".world", value.getWorld().getName());
    super.set(key + ".yaw", value.getYaw());
    super.set(key + ".pitch", value.getPitch());
  }
}
