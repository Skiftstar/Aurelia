package io.skiftstar.aurelia.data.player;

import io.skiftstar.aurelia.data.chunk.ChunkKey;
import io.skiftstar.aurelia.data.config.ConfigManager;
import io.skiftstar.aurelia.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class PlayerData {

  public static Map<UUID, PlayerData> playerData = new HashMap<>();

  private UUID uuid;
  private final List<String> claimedChunks = new ArrayList<>();
  private boolean hasWorld = false;
  private Location lastLocationInWorld = null;
  private ConfigManager configManager = null;

  /**
   * Get the PlayerData for the player with the given UUID
   * @param uuid The UUID of the player
   * @return The PlayerData for the player
   */
  public static PlayerData getPlayerData(UUID uuid) {
    if (playerData.containsKey(uuid)) {
      return playerData.get(uuid);
    }
    PlayerData data = new PlayerData(uuid);
    playerData.put(uuid, data);
    return data;
  }

  private PlayerData(UUID uuid) {    
    this.uuid = uuid;

    Logger.log("Getting PlayerData for " + uuid.toString());

    configManager =
      new ConfigManager(
        ConfigManager.PLAYER_DIRECTORY,
        uuid.toString() + ".yml"
      );
    if (configManager.contains("claimedChunks")) {
      claimedChunks.addAll(configManager.getStringList("claimedChunks"));
    }
    hasWorld = configManager.getBoolean("hasWorld", false);
    lastLocationInWorld =
      configManager.getLocation("lastLocationInWorld", null);
  }

  /**
   * @return Whether the player has a world already created
   */
  public boolean hasWorld() {
    return hasWorld;
  }

  /**
   * Set whether the player has a world already created
   * @param hasWorld Whether the player has a world already created
   */
  public void setHasWorld(boolean hasWorld) {
    this.hasWorld = hasWorld;
  }

  /**
   * @return List of claimed chunks as strings with the format "x;z"
   */
  public List<String> getClaimedChunks() {
    return claimedChunks;
  }

  /**
   * Check if the player has claimed the given chunk
   * @param chunk The chunk to check
   * @return Whether the player has claimed the chunk
   */
  public boolean hasClaimedChunk(Chunk chunk) {
    final String chunkKey = ChunkKey.buildKey(chunk.getX(), chunk.getZ());
    return claimedChunks.contains(chunkKey);
  }

  /**
   * Add a claimed chunk to the player's data
   * @param chunk The chunk to add
   */
  public void addClaimedChunk(Chunk chunk) {
    final String chunkKey = ChunkKey.buildKey(chunk.getX(), chunk.getZ());
    claimedChunks.add(chunkKey);
  }

  /**
   * Set the last location the player was in the world
   * @param lastLocationInWorld The last location the player was in the world
   */
  public void setLastLocationInWorld(Location lastLocationInWorld) {
    this.lastLocationInWorld = lastLocationInWorld;
  }

  /**
   * @return The last location the player was in the world
   */
  public Location getLastLocationInWorld() {
    return lastLocationInWorld;
  }

  /**
   * @return The UUID of the player
   */
  public UUID getUUID() {
      return uuid;
  }

  /**
   * Save the player's data to the config
   */
  public void save() {
    configManager.set("claimedChunks", claimedChunks);
    configManager.set("hasWorld", hasWorld);
    configManager.setLocation("lastLocationInWorld", lastLocationInWorld);
    configManager.save();
  }
}
