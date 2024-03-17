package io.skiftstar.aurelia.listeners;

import io.skiftstar.aurelia.Aurelia;
import io.skiftstar.aurelia.data.player.PlayerData;
import io.skiftstar.aurelia.world.WorldManager;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

  public JoinLeaveListener() {
    Aurelia
      .getInstance()
      .getServer()
      .getPluginManager()
      .registerEvents(this, Aurelia.getInstance());
  }

  @EventHandler
  private void onJoin(PlayerJoinEvent joinEvent) {
    final Player player = joinEvent.getPlayer();
    final UUID playerUUID = player.getUniqueId();
    PlayerData playerData = PlayerData.getPlayerData(playerUUID);

    if (!playerData.hasWorld()) {
      final World playerWorld = WorldManager.initWorld(playerUUID);
      if (playerWorld == null) {
        //TODO: Some kind of error handling
        return;
      }

      playerData.setHasWorld(true);
      playerData.save();
      player.teleport(playerWorld.getSpawnLocation());
      return;
    }

    final World playerWorld = WorldManager.loadWorld(playerUUID);
    if (playerWorld == null) {
      return;
    }
    
    Location lastLocationInWorld = playerData.getLastLocationInWorld();
    if (lastLocationInWorld != null) {
      lastLocationInWorld.setWorld(playerWorld);
      player.teleport(lastLocationInWorld);
    } else {
      player.teleport(playerWorld.getSpawnLocation());
    }
  }

  @EventHandler
  private void onLeave(PlayerQuitEvent e) {
    final Player player = e.getPlayer();
    final UUID playerUUID = player.getUniqueId();
    PlayerData playerData = PlayerData.getPlayerData(playerUUID);

    if (playerData.hasWorld()) {
      if (
        player.getLocation().getWorld().getName().equals(playerUUID.toString())
      ) {
        playerData.setLastLocationInWorld(player.getLocation());
      }
      WorldManager.unloadWorld(playerUUID);
      playerData.save();
    }
  }
}
