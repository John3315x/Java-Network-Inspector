package com.minisiem.model.port;

import java.util.Objects;

public class UdpPort extends Port {

	public UdpPort(String ipDevice, String port, String protocol, String service) {
		super(ipDevice, port, protocol, service);

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
		UdpPort other = (UdpPort) obj;
		return Objects.equals(port, other.port);
	}

	@Override
	public String toString() {
		return "UdpPort [ipDevice=" + ipDevice + ", port=" + port + ", protocol=" + protocol + ", service=" + service
				+ "]";
	}

}
