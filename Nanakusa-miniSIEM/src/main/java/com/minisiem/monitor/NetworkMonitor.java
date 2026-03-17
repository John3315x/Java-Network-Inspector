package com.minisiem.monitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.minisiem.EventIDs;
import com.minisiem.TaskIDs;
import com.minisiem.discovery.HostDiscovery;
import com.minisiem.events.log.TaskManager;
import com.minisiem.events.log.EventManager;
import com.minisiem.model.Device;
import com.minisiem.model.Event;
import com.minisiem.model.Task;
import com.minisiem.net.NetworkContext;

public class NetworkMonitor {

	private final ScheduledExecutorService scheduler;

	private Set<Device> previousHosts = new HashSet<>();

	private final Map<Device, Integer> failureCounters = new HashMap<>();
	private final int maxFailures = 3;
	
	private Task taskIndicator;

	public NetworkMonitor() {
		this.scheduler = Executors.newSingleThreadScheduledExecutor();
		
		// Se crea una INDICACION de actividad en el miniSEAM
		taskIndicator = new Task(TaskIDs.TASK, "Activity monitor");
	}

	/**
	 * 
	 * @param intervalSeconds
	 */
	public void start(int intervalSeconds) {
		TaskManager.subTaskStarted(taskIndicator);
		scheduler.scheduleAtFixedRate(this::monitorTask, 0, intervalSeconds, TimeUnit.SECONDS);		
	}

	/**
	 * 
	 */
	public void stop() {
		scheduler.shutdown();
		TaskManager.subTaskCompleted(taskIndicator);
	}

	private void monitorTask() {

		try {

			Set<Device> currentHosts = new HashSet<>(HostDiscovery.findActiveIPs(NetworkContext.BASE_IP, NetworkContext.START_IP, NetworkContext.END_IP));

			// Detectar nuevos dispositivos
			for (Device device : currentHosts) {

				if (!previousHosts.contains(device)) {

					EventManager.deviceConnected(new Event(EventIDs.DEVICE, "Device event", "Dispositivo detectado: " + device.getIp()));
					
					previousHosts.add(device);
				}

				// Reiniciar contador si volvió a responder
				failureCounters.remove(device);
			}

			// Verificar posibles desconexiones
			Iterator<Device> iterator = previousHosts.iterator();

			while (iterator.hasNext()) {

				Device device = iterator.next();

				if (!currentHosts.contains(device)) {

					int failures = failureCounters.getOrDefault(device, 0) + 1;
					failureCounters.put(device, failures);

					if (failures >= maxFailures) {

						EventManager.deviceConnected(new Event(EventIDs.DEVICE, "Device event", "Dispositivo desconectado: " + device.getIp()));

						failureCounters.remove(device);
						iterator.remove(); // ✅ forma correcta
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
