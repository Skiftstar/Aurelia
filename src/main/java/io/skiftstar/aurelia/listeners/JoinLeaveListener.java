package io.skiftstar.aurelia.listeners;

import io.skiftstar.aurelia.data.player.PlayerData;
import io.skiftstar.aurelia.world.WorldManager;
import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveListener {

  @EventHandler
  private void onJoin(PlayerJoinEvent joinEvent) {
    final UUID playerUUID = joinEvent.getPlayer().getUniqueId();
    PlayerData playerData = PlayerData.getPlayerData(playerUUID);
    if (!playerData.hasWorld()) {
      WorldManager.initWorld(playerUUID);
    }
  }
}
