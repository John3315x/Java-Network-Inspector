package com.minisiem.discovery;

public interface HostDiscoveryStrategy {

	boolean isAlive(String ip);
}
