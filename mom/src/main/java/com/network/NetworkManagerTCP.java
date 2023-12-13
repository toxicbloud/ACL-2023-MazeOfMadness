package com.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Network manager.
 * (server and client)
 */
public abstract class NetworkManagerTCP {
    /** Server socket. */
    private ServerSocket socket;

    /** Server received packets. */
    private List<DatagramPacket> receivedPackets = new ArrayList<>();

    /** Data listeners. */
    private List<DataListener> dataListeners = new ArrayList<>();

    /** Data validators. */
    private List<ValidatorStruct> dataValidators = new ArrayList<>();

    /** Clients list. */
    private List<NetworkInfos> clients = new ArrayList<>();

    /** Network object id. */
    private int id;

    protected NetworkManagerTCP(int port) {
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
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
        this.seekForClients();
        this.getPackets();
        this.processPackets();
    }

    private void seekForClients() {
        try {
            if (socket.isClosed()) {
                return;
            }
            socket.setSoTimeout(1);
            clients.add(new NetworkInfos(socket.accept()));
        } catch (IOException e) {
            if (!e.getMessage().equals("Accept timed out")) {
                System.out.println("Error accepting client: " + e.getMessage());
            }
        }
    }

    /**
     * Get packets.
     */
    private void getPackets() {
        receivedPackets.clear();

        for (NetworkInfos client : clients) {
            try {
                DataInputStream in = new DataInputStream(client.getSocket().getInputStream());
                int available = in.available();
                if (available > 0) {
                    byte[] buffer = new byte[available + client.getDataBufferCache().length];
                    System.arraycopy(client.getDataBufferCache(), 0, buffer, 0, client.getDataBufferCache().length);
                    in.read(buffer, client.getDataBufferCache().length, available);

                    while (buffer.length >= 2) {
                        int dataLength = NetworkDialogs.getIntValue(buffer, 0);
                        if (buffer.length >= dataLength + 2) {
                            byte[] data = new byte[dataLength];
                            System.arraycopy(buffer, 2, data, 0, dataLength);
                            receivedPackets.add(new DatagramPacket(
                                data,
                                dataLength,
                                client.getSocket().getInetAddress(),
                                client.getSocket().getPort()));
                            int newBufferLength = buffer.length - dataLength - 2;
                            byte[] newBuffer = new byte[newBufferLength];
                            System.arraycopy(buffer, dataLength + 2, newBuffer, 0, newBufferLength);
                            buffer = newBuffer;
                        } else {
                            break;
                        }
                    }
                    client.setDataBufferCache(buffer);
                }
            } catch (IOException e) {
                System.out.println("Error receiving packet: " + e.getMessage());
            }
        }
    }

    /**
     * Get the client socket from the packet.
     * @param paquet Packet.
     * @return Client socket.
     */
    private NetworkInfos getPaquetClient(DatagramPacket paquet) {
        for (NetworkInfos client : clients) {
            if (client.getSocket().getInetAddress().equals(paquet.getAddress())
                && client.getSocket().getPort() == paquet.getPort()) {
                return client;
            }
        }
        return null;
    }

    /**
     * Process packets.
     */
    private void processPackets() {
        for (DatagramPacket packet : getReceivedPackets()) {
            NetworkInfos infos = getPaquetClient(packet);
            byte[] data = packet.getData();

            this.onDataReceived(data, infos);
            List<ValidatorStruct> toRemove = new ArrayList<>();
            ValidatorStruct[] validators = dataValidators.toArray(new ValidatorStruct[0]);
            for (ValidatorStruct validator : validators) {
                if (validator.getValidator().isValid(data, infos)) {
                    boolean shouldRemove = validator.getListener().onDataReceived(data, infos);
                    if (shouldRemove) {
                        toRemove.add(validator);
                    }
                }
            }
            dataValidators.removeAll(toRemove);
            List<DataListener> toRemoveListeners = new ArrayList<>();
            for (DataListener listener : getDataListeners()) {
                if (listener != null) {
                    boolean shouldRemove = listener.onDataReceived(data, infos);
                    if (shouldRemove) {
                        toRemoveListeners.add(listener);
                    }
                }
            }
            dataListeners.removeAll(toRemoveListeners);
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
        try {
            byte[] dataWrapped = new byte[data.length + 2];
            System.arraycopy(data, 0, dataWrapped, 2, data.length);
            NetworkDialogs.encodeIntValue(data.length, dataWrapped, 0);
            target.getSocket().getOutputStream().write(dataWrapped);
            return true;
        } catch (IOException e) {
            System.out.println("Error sending data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Send data to default(s) target(s).
     * @param data the data
     * @return true if the data has been sent, false otherwise.
     */
    public abstract boolean sendData(byte[] data);

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
        this.dataValidators.add(new ValidatorStruct(validator, listener));
    }

    /**
     * Listen for one event validated by the validator.
     * @param validator Data validator.
     * @param listener Data listener.
     */
    public void once(DataValidator validator, DataListener listener) {
        this.dataValidators.add(new ValidatorStruct(validator, listener));
    }

    /**
     * getClients.
     * @return getClients
     */
    public List<NetworkInfos> getClients() {
        return clients;
    }

    /**
     * addClient.
     * @param client client to add
     */
    public void addClient(NetworkInfos client) {
        this.clients.add(client);
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

    /**
     * shutdown.
     */
    public void shutdown() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing socket: " + e.getMessage());
        }
    }

    /**
     * getId.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setId.
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }
}
