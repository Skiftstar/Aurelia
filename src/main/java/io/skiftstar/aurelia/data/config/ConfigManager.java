package io.skiftstar.aurelia.data.config;

import io.skiftstar.aurelia.Aurelia;
import java.io.File;
import java.io.IOException;
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
}
