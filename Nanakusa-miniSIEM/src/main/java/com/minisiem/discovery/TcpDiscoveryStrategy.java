package com.minisiem.discovery;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpDiscoveryStrategy implements HostDiscoveryStrategy {

	private final int timeout;
    private final int discoveryPorts[];

    public TcpDiscoveryStrategy(int timeout, int[] discoveryPorts) {
        this.timeout = timeout;
        this.discoveryPorts = discoveryPorts;
    }

	@Override
	public boolean isAlive(String ip) {
		for (int port : discoveryPorts) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ip, port), timeout);               
                return true; // conexión exitosa → host activo
                
            } catch (IOException ignored) {
                // Puede estar cerrado, filtrado o no existir
            }
        }
        return false;
	}

}
