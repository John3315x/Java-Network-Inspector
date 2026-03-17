package com.minisiem.model.port;

import java.util.Objects;

public class TcpPort extends Port {

	public TcpPort(String ipDevice, String port, String protocol, String service) {
		super(ipDevice, port, protocol, service);
	}

	public TcpPort(String port, String protocol) {
		super(port, protocol);
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(port);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TcpPort other = (TcpPort) obj;
		return Objects.equals(port, other.port);
	}

	@Override
	public String toString() {
		return "TcpPort [ipDevice=" + ipDevice + ", port=" + port + ", protocol=" + protocol + ", service=" + service
				+ "]";
	}

}
