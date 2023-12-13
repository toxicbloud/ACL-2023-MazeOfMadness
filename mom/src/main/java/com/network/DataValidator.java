package com.network;

import java.util.EventListener;

/**
 * Data Validator.
 */
public interface DataValidator extends EventListener {
    /**
     * Check if the data is valid.
     * @param data Data received.
     * @param infos Network infos.
     * @return true if the data is valid, false otherwise.
     */
    boolean isValid(byte[] data, NetworkInfos infos);
}
