package com.minisiem.discovery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.minisiem.TaskIDs;
import com.minisiem.core.Kernel;
import com.minisiem.events.log.TaskManager;
import com.minisiem.model.Device;
import com.minisiem.model.Task;
import com.minisiem.net.NetworkContext;
import com.minisiem.repository.DeviceRepository;

public class HostDiscovery {

	private static IcmpDiscoveryStrategy icmpDiscoveryStrategy = new IcmpDiscoveryStrategy(Kernel.icmpTimeout);
	private static TcpDiscoveryStrategy tcpDiscoveryStrategy = new TcpDiscoveryStrategy(Kernel.tcpTimeout,
			NetworkContext.POSSIBLE_PORTS);
	public static HybridDiscoveryStrategy hybridDiscoveryStrategy = new HybridDiscoveryStrategy(icmpDiscoveryStrategy,
			tcpDiscoveryStrategy);

	/**
	 * 
	 * @param baseIp
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Device> findActiveIPs(String baseIp, int start, int end) {
		// 1. Se crea una INDICACION de tarea en el miniSEAM
		Task taskIndicator = new Task(TaskIDs.SUBTASK, "Active IP finder");

		// 2. Task manager se encargar de realizar lo necesario con la tarea
		TaskManager.subTaskStarted(taskIndicator);

		// ====================================================================
		ExecutorService executor = Executors.newFixedThreadPool(Kernel.networkThreadPoolSize);
		List<Future<Device>> futures = new ArrayList<>();

		for (int i = start; i <= end; i++) {

			String ip = baseIp + i;

			try {
				InetAddress address = InetAddress.getByName(ip);

				Callable<Device> task = () -> {

					// Sin estrategia de escaneo de puertos

					if (hybridDiscoveryStrategy.isAlive(ip)) {

						return new Device(ip, address.getHostName(), getMacAddress(address));
					}

					return null;
				};

				futures.add(executor.submit(task));

			} catch (UnknownHostException e) {

				e.printStackTrace();
			}
		}

		executor.shutdown();

		List<Device> activeHosts = new ArrayList<>();

		for (Future<Device> future : futures) {
			try {
				Device device = future.get();

				if (device != null) {

					activeHosts.add(device);
					new DeviceRepository().create(device); // Guardar en la BBDD
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		TaskManager.subTaskCompleted(taskIndicator);
		return activeHosts;
	}

	public static String getMacAddress(InetAddress address) {

		try {
			// 🔎 Detectar si es una IP local (de tu máquina)
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);

			if (ni != null) {
				byte[] mac = ni.getHardwareAddress();

				if (mac != null) {
					return formatMac(mac);
				}
			}

			// 🌐 Si no es local → usar ARP
			return getMacFromArp(address.getHostAddress());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Unknown";
	}

	/**
	 * 
	 * @param ip
	 * @return
	 */
	public static String getMacFromArp(String ip) {
		try {
			Process p = Runtime.getRuntime().exec("arp -a");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

				if (line.contains(ip)) {
					line = line.toUpperCase();

					// Separar por espacios múltiples
					String[] parts = line.trim().split("\\s+");

					for (String part : parts) {
						// Detecta formato MAC (xx-xx-xx-xx-xx-xx o xx:xx...)
						if (part.matches("..[-:].*")) {
							return part;
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Unknown";
	}

	/**
	 * @param mac
	 * @return
	 */
	private static String formatMac(byte[] mac) {
		StringBuilder sb = new StringBuilder();
		for (byte b : mac) {
			sb.append(String.format("%02X:", b));
		}
		return sb.substring(0, sb.length() - 1);
	}

}
