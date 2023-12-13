package com.network;

import java.util.EventListener;

/**
 * Data listener.
 */
public interface DataListener extends EventListener {
    /**
     * Called when data is received.
     * @param data Data received.
     * @param infos Network infos.
     * @return True if the listener should be removed.
     */
    boolean onDataReceived(byte[] data, NetworkInfos infos);
}
