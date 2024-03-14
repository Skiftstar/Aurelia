package io.skiftstar.aurelia.data.world;

import io.skiftstar.aurelia.data.config.ConfigManager;

public class WorldData {
    private static ConfigManager worldConfigManager = new ConfigManager("world.yml");
    private static int startArea = 0; // Square area of the start area (i.e. 3 = 3x3 area)

    public static int getStartArea() {
        if (startArea == 0) {
          startArea = worldConfigManager.getInt("startArea", 3);
        }
        return startArea;
      }
}
