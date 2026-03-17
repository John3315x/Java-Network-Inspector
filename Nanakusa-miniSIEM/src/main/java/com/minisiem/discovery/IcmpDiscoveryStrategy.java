package com.minisiem.discovery;

import java.io.IOException;
import java.net.InetAddress;

public class IcmpDiscoveryStrategy implements HostDiscoveryStrategy {

	private final int timeout;

	public IcmpDiscoveryStrategy(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public boolean isAlive(String ip) {
		try {
            InetAddress address = InetAddress.getByName(ip);          
            return address.isReachable(timeout);
            
		} catch (IOException e) {
            return false;
        }
	}

}
