package io.skiftstar.aurelia.data.chunk;

import static io.skiftstar.aurelia.data.config.KeyConfig.KEY_SEPERATOR;;

public class ChunkKey {

    public static String buildKey(int x, int z) {
        return x + KEY_SEPERATOR + z;
    }

    public static int[] getCoords(String key) {
        String[] split = key.split(KEY_SEPERATOR);
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }
    
}
