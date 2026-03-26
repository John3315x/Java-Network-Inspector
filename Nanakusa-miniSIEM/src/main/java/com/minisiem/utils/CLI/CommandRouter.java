package com.minisiem.utils.CLI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.minisiem.core.Kernel;
import com.minisiem.discovery.HostDiscovery;
import com.minisiem.model.Device;
import com.minisiem.model.port.Port;
import com.minisiem.net.NetworkContext;
import com.minisiem.repository.DeviceRepository;
import com.minisiem.scanner.LocalPortScanner;
import com.minisiem.scanner.NetworkScanner;
import com.minisiem.scanner.PortScanner;

public class CommandRouter {
	
	private final DeviceRepository deviceRepository;

	public CommandRouter() {
		deviceRepository = new DeviceRepository();
	}
	/**
	 * ip discovery, ip d
	 * @return
	 */
	public List<Device> ipDiscovery() {
		return HostDiscovery.findActiveIPs(NetworkContext.BASE_IP, NetworkContext.START_IP, NetworkContext.END_IP);
	}
	
	/**
	 * host 0, h 0
	 * @return
	 */
	public List<Object> getAllDevices() {
		return deviceRepository.getAll();
	}
	
	/**
	 * host -#, host--# h -#, h --#
	 * @param volume
	 * @param order
	 * @return
	 */
	public List<Object> getOrderVolumeDevices(int volume, String order) {
		return deviceRepository.getItems(volume, order);
	}
	
	/**
	 * sap $ #
	 * @param ip
	 * @return
	 */
	public List<Port> scanAllPorts(String ip, int numberPorts) {
		LocalPortScanner localPortScanner = new LocalPortScanner(Kernel.threadPoolSizeAllPort, Kernel.timeoutAllPort);
		return localPortScanner.scanAllPorts(ip, numberPorts);
	}
	
	/**
	 * pfhs
	 * @return
	 */
	public List<Device> performFullHostScan() {
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = new NetworkScanner(new PortScanner(Kernel.portTimeout), Kernel.networkThreadPoolSize).performFullHostScan(NetworkContext.BASE_IP, NetworkContext.START_IP,NetworkContext.END_IP, NetworkContext.COMMON_TCP_PORTS,NetworkContext.COMMON_UDP_PORTS);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return devices;
	}
}
