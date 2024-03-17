package io.skiftstar.aurelia.world;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;

import io.skiftstar.aurelia.data.player.PlayerData;
import io.skiftstar.aurelia.data.world.WorldData;
import io.skiftstar.aurelia.util.Logger;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

public class WorldManager {

  /**
   * Initializes a new world for the player with the given UUID
   * @param playerUUID The UUID of the player
   * @return The new world or null if the world could not be created
   */
  public static World initWorld(UUID playerUUID) {
    Logger.log("Initializing World for " + playerUUID.toString());

    final String worldName = WorldData.getTemplateWorldName();
    final int startArea = WorldData.getStartArea();

    final World templateWorld = Bukkit.getWorld(worldName);
    if (templateWorld == null) {
      Logger.error("Could not find the template world with Name " + worldName);
      return null;
    }

    final WorldCreator worldCreator = new WorldCreator(playerUUID.toString());
    // Empty chunk generator so that it generates a void world
    worldCreator.generator(new ChunkGenerator() {});
    final World newWorld = Bukkit.createWorld(worldCreator);
    Location spawnLocation = new Location(
      newWorld,
      WorldData.getSpawnLocation().getX(),
      WorldData.getSpawnLocation().getY(),
      WorldData.getSpawnLocation().getZ(),
      WorldData.getSpawnLocation().getYaw(),
      WorldData.getSpawnLocation().getPitch()
    );
    newWorld.setSpawnLocation(spawnLocation);

    PlayerData data = PlayerData.getPlayerData(playerUUID);

    for (int x = -startArea; x <= startArea; x++) {
      for (int z = -startArea; z <= startArea; z++) {
        copyChunk(newWorld, x, z);
        data.addClaimedChunk(newWorld.getChunkAt(x, z));
      }
    }

    return newWorld;
  }

  /**
   * Copies a chunk from the template world to the given world
   * @param to The world to copy the chunk to
   * @param chunkX The x coordinate of the chunk
   * @param chunkZ The z coordinate of the chunk
   */
  public static void copyChunk(World to, int chunkX, int chunkZ) {
    final World from = Bukkit.getWorld(WorldData.getTemplateWorldName());
    final int x1 = chunkX * 16;
    final int z1 = chunkZ * 16;
    final int x2 = x1 + 15;
    final int z2 = z1 + 15;
    final BlockVector3 min = BlockVector3.at(
      x1,
      Math.max(from.getMinHeight(), to.getMinHeight()),
      z1
    );
    final BlockVector3 max = BlockVector3.at(
      x2,
      Math.min(from.getMaxHeight(), to.getMaxHeight()),
      z2
    );
    CuboidRegion region = new CuboidRegion(min, max);
    BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

    ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
      BukkitAdapter.adapt(from),
      region,
      clipboard,
      region.getMinimumPoint()
    );

    try {
      Operations.complete(forwardExtentCopy);
    } catch (Exception e) {
      Logger.error("Could not copy chunk " + chunkX + " " + chunkZ);
      e.printStackTrace();
    }

    try (
      EditSession editSession = WorldEdit
        .getInstance()
        .newEditSession(BukkitAdapter.adapt(to))
    ) {
      Operation operation = new ClipboardHolder(clipboard)
        .createPaste(editSession)
        .to(
          BlockVector3.at(
            x1,
            Math.max(from.getMinHeight(), to.getMinHeight()),
            z1
          )
        )
        .build();
      Operations.complete(operation);
    } catch (Exception e) {
      Logger.error("Could not paste chunk " + chunkX + " " + chunkZ);
      e.printStackTrace();
    }
  }

  /**
   * Unloads the world of the player with the given UUID
   * @param playerUUID The UUID of the player
   */
  public static void unloadWorld(UUID playerUUID) {
    final World world = Bukkit.getWorld(playerUUID.toString());
    if (world == null) {
      Logger.error("Could'nt Unload World for player with UUID " + playerUUID);
      return;
    }
    Bukkit.unloadWorld(world, true);
  }

  /**
   * Loads the world of the player with the given UUID
   * @param playerUUID The UUID of the player
   * @return The world of the player or null if the world could not be loaded
   */
  public static World loadWorld(UUID playerUUID) {
    final String worldName = playerUUID.toString();
    new WorldCreator(worldName).createWorld();
    final World world = Bukkit.getWorld(worldName);
    if (world == null) {
      Logger.error("Could not find the world of the player with UUID " + playerUUID);
      return null;
    }
    return world;
  }
}
