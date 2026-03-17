package com.minisiem.model;

import java.util.List;
import java.util.Objects;

import com.minisiem.model.port.Port;

public class Device {
	private final String ip, hostname, mac;
	private final List<Port> openPorts;

	public Device(String ip, String hostname) {
		this.ip = ip;
		this.hostname = hostname;
		this.mac = null;
		this.openPorts = null;
	}

	public Device(String ip, List<Port> openPorts) {
		this.ip = ip;
		this.openPorts = openPorts;
		this.hostname = null;
		this.mac = null;
	}

	public Device(String ip, String hostname, String mac) {
		this.ip = ip;
		this.hostname = hostname;
		this.mac = mac;
		this.openPorts = null;
	}

	public Device(String ip, String hostname, String mac, List<Port> openPorts) {
		this.ip = ip;
		this.hostname = hostname;
		this.mac = mac;
		this.openPorts = openPorts;
	}

	public String getIp() {
		return ip;
	}

	public List<Port> getOpenPorts() {
		return openPorts;
	}

	public String getHostname() {
		return hostname;
	}

	public String getMac() {
		return mac;
	}

	public boolean hasOpenPorts() {
		return openPorts != null && !openPorts.isEmpty();
	}

	@Override
	public int hashCode() {
		return Objects.hash(ip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		return Objects.equals(ip, other.ip);
	}

	@Override
	public String toString() {
		return "Device [ip=" + ip + ", hostname=" + hostname + ", mac=" + mac + ", openPorts=" + openPorts + "]";
	}
}
