package com.minisiem.scanner;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class PortScanner {

	private final int timeout;

	public PortScanner(int timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * Intenta conectarse a puerto TCP
	 * @param ip
	 * @param port
	 * @return
	 */
	public boolean isTcpPortOpen(String ip, int port) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(ip, port), timeout);
			return true;

		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Intenta conectarse a puerto UDP
	 * @param ip
	 * @param port
	 * @return
	 */
	public boolean isUdpPortOpen(String ip, int port) {

		try (DatagramSocket socket = new DatagramSocket()) {

			socket.setSoTimeout(timeout);

			byte[] data = new byte[32];

			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
			
			socket.send(packet);

			DatagramPacket response = new DatagramPacket(new byte[1024], 1024);

			socket.receive(response);

			//System.out.println("UDP responde");
			return true; // hubo respuesta

		} catch (SocketTimeoutException e) {

			//System.out.println("UDP sin respuesta");
			return false; // no respondió
		} catch (IOException e) {
			return false;
		}
	}
}
