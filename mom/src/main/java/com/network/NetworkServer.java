package com.network;

/**
 * Network server.
 */
public class NetworkServer extends NetworkManagerTCP {
    /** Port number. */
    public static final int PORT = 5621;

    /**
     * Constructor.
     */
    public NetworkServer() {
        super(PORT);
    }

    /**
     * Constructor.
     * @param port Port number (default=5621).
     */
    public NetworkServer(int port) {
        super(port);
    }

    @Override
    void onDataReceived(byte[] data, NetworkInfos infos) {

    }

    /**
     * Send data to all clients.
     * @param data Data to send.
     */
    public void sendData(byte[] data) {
        for (NetworkInfos client : this.getClients()) {
            this.sendData(data, client);
        }
    }
}
