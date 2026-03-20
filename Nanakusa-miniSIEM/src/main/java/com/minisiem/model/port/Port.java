package com.minisiem.model.port;

import java.util.Objects;

public class Port {

	protected int task_id;
	protected String ipDevice;
	protected String port_number;
	protected String protocol;
	protected String service;

	public Port() {

	}

	public Port(int task_id, String ipDevice, String port_number, String protocol, String service) {
		super();
		this.task_id = task_id;
		this.ipDevice = ipDevice;
		this.port_number = port_number;
		this.protocol = protocol;
		this.service = service;
	}

	public Port(String port_number, String protocol) {
		super();
		this.ipDevice = "";
		this.port_number = port_number;
		this.protocol = protocol;
		this.service = "";
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public String getIpDevice() {
		return ipDevice;
	}

	public void setIpDevice(String ipDevice) {
		this.ipDevice = ipDevice;
	}

	public String getPort_number() {
		return port_number;
	}

	public void setPort_number(String port_number) {
		this.port_number = port_number;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public int hashCode() {
		return Objects.hash(port_number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Port other = (Port) obj;
		return Objects.equals(port_number, other.port_number);
	}

	@Override
	public String toString() {
		return "Port [port_number=" + port_number + ", protocol=" + protocol + ", service=" + service + "]";
	}

	public String dbFormat() {
		return "Port [task_id=" + task_id + ", ipDevice=" + ipDevice + ", port_number=" + port_number + ", protocol=" + protocol
				+ ", service=" + service + "]";
	}

}
