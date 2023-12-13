package com.network;

/**
 * Network server.
 */
public class NetworkClient extends NetworkManagerTCP {
    /** Server infos. */
    private NetworkInfos serverInfos;

    /**
     * Constructor.
     * Default constructor.
     */
    public NetworkClient() {
        this("localhost", NetworkServer.PORT);
        this.setId(-1);
    }

    /**
     * Constructor.
     * @param ip Server IP address.
     */
    public NetworkClient(String ip) {
        this(ip, NetworkServer.PORT);
    }

    /**
     * Constructor.
     * @param ip Server IP address.
     * @param port Server port number.
     */
    public NetworkClient(String ip, int port) {
        super(0);
        this.serverInfos = new NetworkInfos(port, ip, true);
        this.serverInfos.setId(0);
        this.addClient(serverInfos);
    }

    @Override
    void onDataReceived(byte[] data, NetworkInfos infos) {

    }

    /**
     * Send data to the server.
     * @param data Data to send.
     * @return true if the data has been sent, false otherwise.
     */
    public boolean sendData(byte[] data) {
        return this.sendData(data, this.getServerInfos());
    }

    /**
     * Get the server infos.
     * @return Server infos.
     */
    public NetworkInfos getServerInfos() {
        return serverInfos;
    }
}
