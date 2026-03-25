package com.minisiem.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.minisiem.model.port.Port;

public class Device {
	private int id;
	private String ip, hostname, mac;
	private List<Port> openPorts;
	private LocalDateTime seenFirstTime;

	public Device() {
		
	}

	public Device(String ip, String hostname) {
		this.ip = ip;
		this.hostname = hostname;
		this.seenFirstTime = LocalDateTime.now();
	}

	public Device(String ip, List<Port> openPorts) {
		this.ip = ip;
		this.openPorts = openPorts;
		this.seenFirstTime = LocalDateTime.now();
	}

	public Device(String ip, String hostname, String mac) {
		this.ip = ip;
		this.hostname = hostname;
		this.mac = mac;
		this.seenFirstTime = LocalDateTime.now();
	}

	public Device(String ip, String hostname, String mac, List<Port> openPorts) {
		this.ip = ip;
		this.hostname = hostname;
		this.mac = mac;
		this.openPorts = openPorts;
		this.seenFirstTime = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public List<Port> getOpenPorts() {
		return openPorts;
	}

	public void setOpenPorts(List<Port> openPorts) {
		this.openPorts = openPorts;
	}

	public LocalDateTime getSeenFirstTime() {
		return seenFirstTime;
	}

	public void setSeenFirstTime(LocalDateTime seenFirstTime) {
		this.seenFirstTime = seenFirstTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ip, mac);
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
		return Objects.equals(ip, other.ip) && Objects.equals(mac, other.mac);
	}

	@Override
	public String toString() {
		return "Device [ip=" + ip + ", hostname=" + hostname + ", mac=" + mac + ", openPorts=" + openPorts + "]";
	}

	public String dbFormat() {
		return "Device [id=" + id + ", ip=" + ip + ", hostname=" + hostname + ", mac=" + mac + ", seenFirstTime=" + seenFirstTime + "]";
	}
}
