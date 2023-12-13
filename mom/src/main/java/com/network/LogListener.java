package com.network;

import java.util.EventListener;

/**
 * Log listener.
 */
public interface LogListener extends EventListener {
    /**
     * Called when logging a message.
     * @param data Message to log.
     * @param clearLastLine Clear the last line or not.
     */
    void onLogging(String data, boolean clearLastLine);
}
