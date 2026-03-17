package com.minisiem.model.port;

public class Port {

	protected final String ipDevice;
	protected final String port;
	protected final String protocol;
	protected final String service;

	/**
	 * @param port
	 * @param protocol
	 * @param service
	 */
	public Port(String ipDevice, String port, String protocol, String service) {
		super();
		this.ipDevice = ipDevice;
		this.port = port;
		this.protocol = protocol;
		this.service = service;
	}

	public Port(String port, String protocol) {
		super();
		this.ipDevice = "";
		this.port = port;
		this.protocol = protocol;
		this.service = "";
	}

	public String getIpDevice() {
		return ipDevice;
	}

	public String getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getService() {
		return service;
	}

	@Override
	public String toString() {
		return "Port [ipDevice=" + ipDevice + ", port=" + port + ", protocol=" + protocol + ", service=" + service
				+ "]";
	}

}
