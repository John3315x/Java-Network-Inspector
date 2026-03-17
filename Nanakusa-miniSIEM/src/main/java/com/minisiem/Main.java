package com.minisiem;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.minisiem.core.Kernel;
import com.minisiem.discovery.HostDiscovery;
import com.minisiem.model.Device;
import com.minisiem.model.port.Port;
import com.minisiem.model.port.TcpPort;
import com.minisiem.monitor.NetworkMonitor;
import com.minisiem.net.NetworkContext;
import com.minisiem.scanner.LocalPortScanner;
import com.minisiem.scanner.NetworkScanner;
import com.minisiem.scanner.PortScanner;

public class Main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		/**
		 * Descubrir IPs Activas
		 */

		/*
		 * List<Device> list = new HostDiscovery().findActiveIPs(NetworkContext.BASE_IP,
		 * NetworkContext.START_IP, NetworkContext.END_IP);
		 * list.forEach(System.out::println);
		 */

		/**
		 * Escaneo de puertos 65535 puertos locales con el metodo TCP
		 */

		LocalPortScanner localPortScanner = new LocalPortScanner(Kernel.threadPoolSizeAllPort, Kernel.timeoutAllPort);
		List<Port> list = localPortScanner.scanAllPorts("10.171.170.205");
		list.forEach(System.out::println);

		/**
		 * Realiza un escaneo completo de host activos en la red
		 */

		/*
		 * try { List<Device> list = new NetworkScanner(new
		 * PortScanner(Kernel.portTimeout),
		 * Kernel.networkThreadPoolSize).performFullHostScan(NetworkContext.BASE_IP,
		 * NetworkContext.START_IP,
		 * NetworkContext.END_IP,NetworkContext.COMMON_TCP_PORTS,
		 * NetworkContext.COMMON_UDP_PORTS); list.forEach(System.out::println); } catch
		 * (IOException e) {
		 * 
		 * }
		 */

		/**
		 * Monitore de actividad
		 */
		/* new NetworkMonitor().start(15); */

	}

}
