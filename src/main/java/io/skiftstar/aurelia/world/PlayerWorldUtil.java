package io.skiftstar.aurelia.world;

import org.bukkit.Chunk;

import io.skiftstar.aurelia.data.player.PlayerData;

public class PlayerWorldUtil {
    

    public static void claimChunk(PlayerData data, Chunk chunk) {
        if (data.hasClaimedChunk(chunk)) {
            return;
        }
        WorldManager.copyChunk(WorldManager.loadWorld(data.getUUID()), chunk.getX(), chunk.getZ());
        data.addClaimedChunk(chunk);
        data.save();
    }

}
