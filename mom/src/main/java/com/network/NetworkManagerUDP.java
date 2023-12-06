package com.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Network manager.
 * (server and client)
 */
public abstract class NetworkManagerUDP {
    /** Packet size. */
    public static final int PACKET_SIZE = 1024;

    /** Max packets per update. */
    private static final int MAX_PAQUETS_PER_UPDATE = 100;

    /** Server socket. */
    private DatagramSocket socket;

    /** Server received packets. */
    private List<DatagramPacket> receivedPackets = new ArrayList<>();

    /** Data listeners. */
    private List<DataListener> dataListeners = new ArrayList<>();

    /** Data validators. */
    private List<ValidatorStruct> dataValidators = new ArrayList<>();

    /** Clients list. */
    private List<NetworkInfos> clients = new ArrayList<>();

    protected NetworkManagerUDP(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("Error creating server socket: " + e.getMessage());
            return;
        }

        try {
            this.socket.setSoTimeout(1);
        } catch (SocketException e) {
            System.out.println("Error setting socket timeout: " + e.getMessage());
            return;
        }
    }

    /**
     * Update the server.
     * (get packets, send packets, etc.)
     */
    public void update() {
        this.getPackets();
        this.processPackets();
    }

    /**
     * Get packets.
     */
    private void getPackets() {
        receivedPackets.clear();

        boolean getPacketFailed = false;
        int nbPaquets = 0;
        while (!getPacketFailed && nbPaquets++ < MAX_PAQUETS_PER_UPDATE) {
            byte[] buffer = new byte[PACKET_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                this.socket.receive(packet);
                NetworkInfos clientInfos = new NetworkInfos(packet.getPort(), packet.getAddress().getHostAddress());
                if (!clients.contains(clientInfos)) {
                    clients.add(clientInfos);
                }
                receivedPackets.add(packet);
            } catch (SocketTimeoutException e) {
                getPacketFailed = true;
            } catch (IOException e) {
                System.out.println("Error receiving packet: " + e.getMessage());
                getPacketFailed = true;
            }
        }
    }

    /**
     * Process packets.
     */
    private void processPackets() {
        for (DatagramPacket packet : getReceivedPackets()) {
            NetworkInfos infos = new NetworkInfos(packet.getPort(), packet.getAddress().getHostAddress());
            byte[] data = packet.getData();

            this.onDataReceived(data, infos);
            List<ValidatorStruct> toRemove = new ArrayList<>();
            ValidatorStruct[] validators = dataValidators.toArray(new ValidatorStruct[0]);
            for (ValidatorStruct validator : validators) {
                if (validator.getValidator().isValid(data, infos)) {
                    validator.getListener().onDataReceived(data, infos);
                    if (validator.isTemporary()) {
                        toRemove.add(validator);
                    }
                }
            }
            dataValidators.removeAll(toRemove);
            for (DataListener listener : getDataListeners()) {
                if (listener != null) {
                    listener.onDataReceived(data, infos);
                }
            }
        }
    }

    abstract void onDataReceived(byte[] data, NetworkInfos infos);

    /**
     * Send data to the target.
     * @param data the data
     * @param target the target
     * @return true if the data has been sent, false otherwise.
     */
    public boolean sendData(byte[] data, NetworkInfos target) {
        return this.sendData(data, target.getIpAddress(), target.getPort());
    }

    /**
     * Send data to a client.
     * @param data Data to send.
     * @param address Client address.
     * @param port Client port.
     * @return true if the data has been sent, false otherwise.
     */
    public boolean sendData(byte[] data, String address, int port) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            System.out.println("Error getting address: " + e.getMessage());
            return false;
        }

        DatagramPacket packet = new DatagramPacket(data, data.length, inetAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            System.out.println("Error sending packet: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Add data listener.
     * @param listener Data listener.
     */
    public void addDataListener(DataListener listener) {
        this.dataListeners.add(listener);
    }

    /**
     * Listen for all events validated by the validator.
     * @param validator Data validator.
     * @param listener Data listener.
     */
    public void when(DataValidator validator, DataListener listener) {
        this.dataValidators.add(new ValidatorStruct(validator, listener, false));
    }

    /**
     * Listen for one event validated by the validator.
     * @param validator Data validator.
     * @param listener Data listener.
     */
    public void once(DataValidator validator, DataListener listener) {
        this.dataValidators.add(new ValidatorStruct(validator, listener, true));
    }

    /**
     * getSocket.
     * @return getSocket
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * getClients.
     * @return getClients
     */
    public List<NetworkInfos> getClients() {
        return clients;
    }

    /**
     * getDataListeners.
     * @return getDataListeners
     */
    public List<DataListener> getDataListeners() {
        return dataListeners;
    }

    /**
     * getReceivedPackets.
     * @return getReceivedPackets
     */
    public List<DatagramPacket> getReceivedPackets() {
        return receivedPackets;
    }

    /**
     * getPort.
     * @return getPort
     */
    public int getPort() {
        return socket.getLocalPort();
    }
}
