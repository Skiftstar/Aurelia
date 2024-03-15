package io.skiftstar.aurelia.util;

import io.skiftstar.aurelia.Aurelia;

public class Logger {

    public static void log(String message) {
        Aurelia.getInstance().getLogger().info(message);
    }
    
    public static void error(String message) {
        Aurelia.getInstance().getLogger().severe(message);
    }
    
}
