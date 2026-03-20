package com.minisiem.scanner;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.minisiem.TaskIDs;
import com.minisiem.discovery.HostDiscovery;
import com.minisiem.events.log.TaskManager;
import com.minisiem.model.Device;
import com.minisiem.model.Task;
import com.minisiem.model.port.Port;
import com.minisiem.model.port.TcpPort;
import com.minisiem.model.port.UdpPort;
import com.minisiem.repository.DeviceRepository;
import com.minisiem.repository.PortRepository;

/**
 * Clase que contiene metodos de escaneo de red de alto procesamiento, por lo
 * general metodos con varias funciones internas.
 * 
 * scanRange() Escanea el rango de IPs proporcionado dentro de una direccion IP
 * fija, asi como la lista de puertos dada y devuelbe un objeto Device
 */
public class NetworkScanner {
	private final PortScanner portScanner;
	private final int threadPoolSize;
	private DeviceRepository deviceRepository;

	public NetworkScanner(PortScanner portScanner, int threadPoolSize) {
		this.portScanner = portScanner;
		this.threadPoolSize = threadPoolSize;
		this.deviceRepository = new DeviceRepository();
	}

	public List<Device> performFullHostScan(String baseIp, int start, int end, int[] tcpPorts, int[] udpPorts)
			throws IOException {
		// Se crea una INDICACION de tarea en el miniSEAM
		Task taskIndicator = new Task(TaskIDs.SUBTASK, "Perform Full Host Scan");
		int task_id = TaskManager.subTaskStartedAndGetId(taskIndicator);

		PortRepository portRepository = new PortRepository(task_id);

		// Creamos un pool fijo de hilos para controlar concurrencia.
		// Esto evita crear un hilo por cada IP (lo cual sería ineficiente).
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

		// Lista que almacenará los resultados futuros de cada tarea enviada al pool.
		List<Future<Device>> futures = new ArrayList<>();

		// Es una herramienta que nos servira más tarde 😂
		ServiceDetector detector = new ServiceDetector();

		// Recorremos el rango de IPs especificado.
		for (int i = start; i <= end; i++) {

			String ip = baseIp + i;
			// Se obtienen ademas hostname y MAC address
			InetAddress address = InetAddress.getByName(ip); // es quien requiere try catch

			// Cada IP se convierte en una tarea Callable que devolverá un Device.
			Callable<Device> task = () -> {

				// 🔎 FASE 1: verificar si el host está activo
				if (!HostDiscovery.hybridDiscoveryStrategy.isAlive(ip)) {// GOD
					return null;
				}

				// Intenta guardarlo en la BD
				// new DeviceRepository().

				String hostname = address.getHostName();
				String macAddress = HostDiscovery.getMacAddress(address);

				// 🔍 FASE 2: escaneo de puertos
				List<Port> openPorts = new ArrayList<>();

				// POOL PARA ESCANEO DE PUERTOS
				ExecutorService portExecutor = Executors.newFixedThreadPool(threadPoolSize);
				List<Future<Port>> portFutures = new ArrayList<>();

				// TCP
				for (int port : tcpPorts) {

					Callable<Port> portTask = () -> {

						if (portScanner.isTcpPortOpen(ip, port)) {
							return new TcpPort(task_id, ip, String.valueOf(port), "TCP", detector.detectTcpService(ip, port));
						}

						return null;
					};
					portFutures.add(portExecutor.submit(portTask));
				}

				// UDP
				for (int port : udpPorts) {

					Callable<Port> portTask = () -> {

						if (portScanner.isUdpPortOpen(ip, port)) {
							return new UdpPort(task_id, ip, String.valueOf(port), "UDP", detector.detectUdpService(port));
						}

						return null;
					};

					portFutures.add(portExecutor.submit(portTask));
				}

				portExecutor.shutdown();// 🚩

				// recoger resultados
				for (Future<Port> future : portFutures) {

					try {

						Port port = future.get();

						if (port != null) {
							// Aqui no se puede guardar el puerto en la BD porque no ha sido guardado el
							// dispositivo aún
							openPorts.add(port);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// SI EL HOTS NO TIENE PUERTOS ACTIVOS, NO LO AGREGA A LA LISTA
				/*
				 * if (openPorts.isEmpty()) { return null; // activo pero sin puertos abiertos }
				 */

				return new Device(ip, hostname, macAddress, openPorts);
			};

			// Enviamos la tarea al pool para ejecución concurrente.
			// submit() devuelve un Future que representa el resultado futuro.
			futures.add(executor.submit(task));
		}

		// Indicamos que no se enviarán más tareas.
		executor.shutdown();

		// Lista final de dispositivos detectados con puertos abiertos.
		List<Device> devices = new ArrayList<>();

		// Recorremos todos los Future para obtener los resultados reales.
		for (Future<Device> future : futures) {
			try {
				// future.get() bloquea hasta que la tarea termine.
				Device device = future.get();

				if (device != null) {
					devices.add(device);

					// Guardar Device en BD
					deviceRepository.create(device);

					// Guardar Port en BD - Poco eficiente
					for (Port port : device.getOpenPorts()) {
						portRepository.create(port);
					}
				}
			} catch (InterruptedException | ExecutionException e) {
				// Manejo básico de errores.
				// En versión profesional aquí iría logging estructurado.
				e.printStackTrace();
			}
		}

		// Devolvemos la lista final lista para ser usada por:
		// - Base de datos
		// - Motor de alertas
		// - API REST
		// - UI

		TaskManager.subTaskCompleted(taskIndicator);

		// Guardar los puertos en la BD

		return devices;
	}

}
