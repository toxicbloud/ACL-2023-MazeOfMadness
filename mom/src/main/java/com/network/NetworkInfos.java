package com.network;

import java.io.IOException;
import java.net.Socket;

/**
 * Network infos.
 */
public class NetworkInfos {
    /** Port number. */
    private int port;

    /** IP address. */
    private String ipAddress;

    /** Socket. */
    private Socket socket;

    /** Data buffer cache. */
    private byte[] dataBufferCache = new byte[0];

    /** Temp. */
    private int temp;

    /**
     * Constructor.
     * @param port Port number.
     * @param ipAddress IP address.
     */
    public NetworkInfos(int port, String ipAddress) {
        this(port, ipAddress, false);
    }

    /**
     * Constructor.
     * @param port Port number.
     * @param ipAddress IP address.
     * @param createSocket Create a socket or not.
     */
    public NetworkInfos(int port, String ipAddress, boolean createSocket) {
        this.port = port;
        this.ipAddress = ipAddress;

        if (createSocket) {
            try {
                this.socket = new Socket(ipAddress, port);
                socket.setSoTimeout(1);
            } catch (IOException e) {
                System.out.println("Error creating socket: " + e.getMessage());
            }
        }
    }

    /**
     * Constructor.
     * @param socket TCP Socket.
     */
    public NetworkInfos(Socket socket) {
        this.socket = socket;
        this.port = socket.getPort();
        this.ipAddress = socket.getInetAddress().getHostAddress();
    }

    /**
     * Get the port number.
     * @return Port number.
     */
    public int getPort() {
        return port;
    }

    /**
     * Get the IP address.
     * @return IP address.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Get the socket.
     * @return Socket.
     */
    public Socket getSocket() {
        return socket;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NetworkInfos)) {
            return false;
        }

        NetworkInfos infos = (NetworkInfos) obj;
        return infos.getIpAddress().equals(this.getIpAddress()) && infos.getPort() == this.getPort();
    }

    @Override
    public int hashCode() {
        return this.getIpAddress().hashCode() + this.getPort();
    }

    @Override
    public String toString() {
        return this.getIpAddress() + ":" + this.getPort();
    }

    /**
     * Get the data buffer cache.
     * @return Data buffer cache.
     */
    public byte[] getDataBufferCache() {
        return dataBufferCache;
    }

    /**
     * Set the data buffer cache.
     * @param dataBufferCache Data buffer cache.
     */
    public void setDataBufferCache(byte[] dataBufferCache) {
        this.dataBufferCache = dataBufferCache;
    }

    /**
     * Get the temp.
     * @return Temp.
     */
    public int getTemp() {
        return temp;
    }

    /**
     * Set the temp.
     * @param temp Temp.
     */
    public void setTemp(int temp) {
        this.temp = temp;
    }
}
