package com.minisiem.core;

public class Kernel {
	// BASIC TIMEOUTS
	// host
	public static int icmpTimeout = 1000;
	public static int tcpTimeout = 500;
	// port
	public static int portTimeout = 500;

	// THREADS
	public static int networkThreadPoolSize = 50;

	// Para cuando se escanean los 65535
	public static int threadPoolSizeAllPort = 150;
	public static int timeoutAllPort = 1000;
}
