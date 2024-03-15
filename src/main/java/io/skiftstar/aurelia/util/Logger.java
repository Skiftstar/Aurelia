package io.skiftstar.aurelia.util;

import io.skiftstar.aurelia.Aurelia;

public class Logger {

    /**
     * Logs a message
     * @param message The message to log
     */
    public static void log(String message) {
        Aurelia.getInstance().getLogger().info(message);
    }
    
    /**
     * Logs an error message
     * @param message The message to log
     */
    public static void error(String message) {
        Aurelia.getInstance().getLogger().severe(message);
    }
    
}
