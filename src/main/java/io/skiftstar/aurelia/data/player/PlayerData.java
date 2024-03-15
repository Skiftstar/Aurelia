package io.skiftstar.aurelia.data.player;

import io.skiftstar.aurelia.data.chunk.ChunkKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerData {

  public static Map<UUID, PlayerData> playerData = new HashMap<>();

  private UUID uuid;
  private final List<String> claimedChunks = new ArrayList<>();
  private boolean hasWorld = false;

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
    YamlConfiguration playerData = PlayerDataManager.getPlayerData(uuid);
    if (playerData.contains("claimedChunks")) {
      claimedChunks.addAll(playerData.getStringList("claimedChunks"));
    }
    hasWorld = playerData.getBoolean("hasWorld", false);
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
   * Add a claimed chunk to the player's data
   * @param chunk The chunk to add
   */
  public void addClaimedChunk(Chunk chunk) {
    final String chunkKey = ChunkKey.buildKey(chunk.getX(), chunk.getZ());
    claimedChunks.add(chunkKey);
  }

  /**
   * Save the player's data to the config
   */
  public void save() {
    YamlConfiguration playerData = PlayerDataManager.getPlayerData(uuid);
    playerData.set("claimedChunks", claimedChunks);
    playerData.set("hasWorld", hasWorld);
    PlayerDataManager.saveConfig(uuid, playerData);
  }
}
