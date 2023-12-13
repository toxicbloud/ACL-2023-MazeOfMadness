package com.network;

/**
 * Network server.
 */
public class NetworkServer extends NetworkManagerTCP {
    /** Port number. */
    public static final int PORT = 5621;

    /** Client ID counter. */
    private int clientIdCounter = 1;

    /**
     * Constructor.
     */
    public NetworkServer() {
        super(PORT);
        this.setId(0);
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

    /**
     * Get the next client id.
     * @return Next client id.
     */
    public int getNextClientId() {
        return clientIdCounter++;
    }
}
