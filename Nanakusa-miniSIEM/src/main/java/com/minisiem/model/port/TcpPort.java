package com.minisiem.model.port;


public class TcpPort extends Port {

	public TcpPort(int task_id, String ipDevice, String port, String protocol, String service) {
		super(task_id, ipDevice, port, protocol, service);
	}
	

	public TcpPort(String port, String protocol) {
		super(port, protocol);
	}

}
