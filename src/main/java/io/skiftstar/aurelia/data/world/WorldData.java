package io.skiftstar.aurelia.data.world;

import io.skiftstar.aurelia.data.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * WorldData is a class that contains all Config data related to the world
 */
public class WorldData {

  private static ConfigManager worldConfigManager = new ConfigManager(
    ConfigManager.CONFIG_DIRECTORY,
    "world.yml"
  );
  private static int startArea = 0; // Radius of the start area
  private static String templateWorldName = "world"; // Name of the template world
  private static Location spawnLocation = null; // The spawn location

  /**
   * Get the Radius of the start area
   * @return The Radius of the start area
   */
  public static int getStartArea() {
    if (startArea == 0) {
      startArea = worldConfigManager.getInt("startArea", 1);
    }
    return startArea;
  }

  /**
   * Get the name of the template world
   * @return The name of the template world
   */
  public static String getTemplateWorldName() {
    if (templateWorldName == null) {
      templateWorldName =
        worldConfigManager.getString("templateWorldName", "world");
    }
    return templateWorldName;
  }

  /**
   * Get the spawn location
   * @return The spawn location
   */
  public static Location getSpawnLocation() {
    if (spawnLocation == null) {
      spawnLocation =
        worldConfigManager.getLocation(
          "spawnLocation",
          new Location(Bukkit.getWorlds().get(0), 0, 0, 0)
        );
    }
    return spawnLocation;
  }
}
