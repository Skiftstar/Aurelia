package io.skiftstar.aurelia;

import org.bukkit.plugin.java.JavaPlugin;

public final class Aurelia extends JavaPlugin {

  private static Aurelia instance;

  @Override
  public void onEnable() {
    instance = this;
    saveConfig(); //Creates default config if it doesn't exist
  }

  @Override
  public void onDisable() {}

  public static Aurelia getInstance() {
    return instance;
  }
}
