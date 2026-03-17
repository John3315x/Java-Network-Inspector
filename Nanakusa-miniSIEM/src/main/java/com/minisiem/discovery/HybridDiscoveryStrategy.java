package com.minisiem.discovery;

public class HybridDiscoveryStrategy implements HostDiscoveryStrategy {

	private final IcmpDiscoveryStrategy icmpStrategy;
	private final TcpDiscoveryStrategy tcpStrategy;

	public HybridDiscoveryStrategy(IcmpDiscoveryStrategy icmpStrategy, TcpDiscoveryStrategy tcpStrategy) {
		this.icmpStrategy = icmpStrategy;
		this.tcpStrategy = tcpStrategy;
	}

	@Override
	public boolean isAlive(String ip) {
		// 1️ Intentar ICMP primero
		if (icmpStrategy.isAlive(ip)) {
			return true;
		}

		// 2️ Fallback a TCP
		return tcpStrategy.isAlive(ip);
	}

}
