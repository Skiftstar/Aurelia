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

  public static PlayerData getPlayerData(UUID uuid) {
    if (playerData.containsKey(uuid)) {
      return playerData.get(uuid);
    }
    PlayerData data = new PlayerData(uuid);
    playerData.put(uuid, data);
    return data;
  }

  public PlayerData(UUID uuid) {
    this.uuid = uuid;
    YamlConfiguration playerData = PlayerDataManager.getPlayerData(uuid);
    if (playerData.contains("claimedChunks")) {
      claimedChunks.addAll(playerData.getStringList("claimedChunks"));
    }
    hasWorld = playerData.getBoolean("hasWorld", false);
  }

  public boolean hasWorld() {
    return hasWorld;
  }

  public void setHasWorld(boolean hasWorld) {
    this.hasWorld = hasWorld;
  }

  public List<String> getClaimedChunks() {
    return claimedChunks;
  }

  public void addClaimedChunk(Chunk chunk) {
    final String chunkKey = ChunkKey.buildKey(chunk.getX(), chunk.getZ());
    claimedChunks.add(chunkKey);
  }

  public void save() {
    YamlConfiguration playerData = PlayerDataManager.getPlayerData(uuid);
    playerData.set("claimedChunks", claimedChunks);
    playerData.set("hasWorld", hasWorld);
    PlayerDataManager.saveConfig(uuid, playerData);
  }
}
