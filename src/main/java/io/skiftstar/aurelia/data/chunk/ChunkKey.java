package io.skiftstar.aurelia.data.chunk;

import static io.skiftstar.aurelia.data.config.KeyConfig.KEY_SEPERATOR;;

public class ChunkKey {

    /**
     * Build a key from the x and z coordinates
     * @param x The x coordinate
     * @param z The z coordinate
     * @return The key
     */
    public static String buildKey(int x, int z) {
        return x + KEY_SEPERATOR + z;
    }

    /**
     * Get the x and z coordinates from the key
     * @param key The key
     * @return The x and z coordinates
     */
    public static int[] getCoords(String key) {
        String[] split = key.split(KEY_SEPERATOR);
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }
    
}
