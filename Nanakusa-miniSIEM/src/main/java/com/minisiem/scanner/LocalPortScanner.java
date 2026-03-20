package com.minisiem.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.minisiem.TaskIDs;
import com.minisiem.events.log.TaskManager;
import com.minisiem.model.Task;
import com.minisiem.model.port.Port;
import com.minisiem.model.port.TcpPort;
import com.minisiem.model.port.UdpPort;
import com.minisiem.repository.PortRepository;

public class LocalPortScanner {

	private final int threadPoolSize;
	private PortRepository portRepository;
	private PortScanner portScanner;

	public LocalPortScanner(int threadPoolSize, int timeout) {
		this.threadPoolSize = threadPoolSize;
		this.portScanner = new PortScanner(timeout);
	}

	/**
	 * 
	 * @param ip
	 * @return
	 * 
	 *         Mejora avanzada (recomendada): El código anterior guarda 65535
	 *         Future. Eso funciona, pero consume memoria.Mejor usar
	 *         CompletionService.
	 */
	public List<Port> scanAllPorts(String ip) {
		// Se crea una INDICACION de tarea en el miniSEAM
		Task taskIndicator = new Task(TaskIDs.SUBTASK, "Local port detection");
		int task_id = TaskManager.subTaskStartedAndGetId(taskIndicator);
		
		portRepository = new PortRepository(task_id);//los puertos registran el id de la tarea que los consultó

		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
		List<Future<Port>> futures = new ArrayList<>();

		ServiceDetector serviceDetector = new ServiceDetector();

		for (int port = 1; port <= 65535; port++) {

			final int currentPort = port;

			Callable<Port> task = () -> {

				if (portScanner.isTcpPortOpen(ip, currentPort)) {
					return new TcpPort(task_id, ip, currentPort + "", "TCP", serviceDetector.detectTcpService(ip, currentPort));
				} 
				
				if (portScanner.isUdpPortOpen(ip, currentPort)) {
					return new UdpPort(task_id, ip, currentPort + "", "UDP", serviceDetector.detectUdpService(currentPort));
				}

				return null;
			};

			futures.add(executor.submit(task));
		} // for

		executor.shutdown();
		List<Port> openPorts = new ArrayList<>();

		for (Future<Port> future : futures) {
			try {
				Port port = future.get();
				if (port != null) {
					openPorts.add(port);
					portRepository.create(port);
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		TaskManager.subTaskCompleted(taskIndicator);
		return openPorts;
	}

}
