package io.skiftstar.aurelia.data.player;

import io.skiftstar.aurelia.Aurelia;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerDataManager {

  private static void createDataFileIfNotExists(final UUID uuid) {
    final File playersDirectory = new File(
      Aurelia.getInstance().getDataFolder(),
      "players"
    );
    if (!playersDirectory.exists()) {
      playersDirectory.mkdirs();
    }

    final File playersFile = new File(
      playersDirectory,
      uuid.toString() + ".yml"
    );

    if (!playersFile.exists()) {
      try {
        playersFile.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static YamlConfiguration getPlayerData(final UUID uuid) {
    createDataFileIfNotExists(uuid);

    final File playersDirectory = new File(
      Aurelia.getInstance().getDataFolder(),
      "players"
    );
    final File playersFile = new File(
      playersDirectory,
      uuid.toString() + ".yml"
    );

    return YamlConfiguration.loadConfiguration(playersFile);
  }

  public static void saveConfig(UUID uuid, YamlConfiguration config) {
    createDataFileIfNotExists(uuid);

    final File playersDirectory = new File(
      Aurelia.getInstance().getDataFolder(),
      "players"
    );
    final File playersFile = new File(
      playersDirectory,
      uuid.toString() + ".yml"
    );

    try {
      config.save(playersFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
