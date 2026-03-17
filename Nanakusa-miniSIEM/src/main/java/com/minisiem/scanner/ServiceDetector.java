package com.minisiem.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ServiceDetector {
	
	public final HashMap<Integer, String> udp_services = new HashMap<Integer, String>();
	
	public ServiceDetector() {

		udp_services.put(53, "DNS");
		udp_services.put(67, "DHCP Server");
		udp_services.put(68, "DHCP Client");
		udp_services.put(69, "TFTP");
		udp_services.put(88, "Kerberos");
		udp_services.put(111, "RPCBind");
		udp_services.put(119, "NNTP");
		udp_services.put(123, "NTP");
		udp_services.put(135, "Microsoft RPC");
		udp_services.put(137, "NetBIOS Name");
		udp_services.put(138, "NetBIOS Datagram");
		udp_services.put(161, "SNMP");
		udp_services.put(162, "SNMP Trap");
		udp_services.put(177, "XDMCP");
		udp_services.put(389, "LDAP");
		udp_services.put(427, "SLP");
		udp_services.put(443, "QUIC / HTTPS UDP");
		udp_services.put(445, "SMB");
		udp_services.put(464, "Kerberos Password");
		udp_services.put(500, "ISAKMP / IPsec");
		udp_services.put(514, "Syslog");
		udp_services.put(515, "LPD Printing");
		udp_services.put(520, "RIP");
		udp_services.put(623, "IPMI");
		udp_services.put(631, "IPP Printing");
		udp_services.put(636, "LDAPS");
		udp_services.put(989, "FTPS Data");
		udp_services.put(990, "FTPS Control");
		udp_services.put(993, "IMAPS");
		udp_services.put(995, "POP3S");
		udp_services.put(1194, "OpenVPN");
		udp_services.put(1434, "SQL Server Browser");
		udp_services.put(1701, "L2TP VPN");
		udp_services.put(1812, "RADIUS Auth");
		udp_services.put(1813, "RADIUS Accounting");
		udp_services.put(1900, "SSDP");
		udp_services.put(2049, "NFS");
		udp_services.put(2222, "DirectAdmin");
		udp_services.put(2483, "Oracle DB");
		udp_services.put(2484, "Oracle DB SSL");
		udp_services.put(3050, "Firebird DB");
		udp_services.put(3074, "Xbox Live");
		udp_services.put(3128, "Proxy");
		udp_services.put(3306, "MySQL");
		udp_services.put(3389, "RDP");
		udp_services.put(3478, "STUN");
		udp_services.put(3690, "Subversion");
		udp_services.put(3702, "WS-Discovery");
		udp_services.put(4444, "Metasploit");
		udp_services.put(4500, "IPsec NAT-T");
		udp_services.put(4569, "Asterisk SIP");
		udp_services.put(4662, "eDonkey");
		udp_services.put(4672, "eMule");
		udp_services.put(4899, "RAdmin");
		udp_services.put(5000, "UPnP");
		udp_services.put(5060, "SIP");
		udp_services.put(5061, "SIP TLS");
		udp_services.put(5190, "ICQ");
		udp_services.put(5222, "XMPP");
		udp_services.put(5223, "XMPP SSL");
		udp_services.put(5353, "mDNS");
		udp_services.put(5432, "PostgreSQL");
		udp_services.put(5555, "Android Debug");
		udp_services.put(5601, "Kibana");
		udp_services.put(5632, "PCAnywhere");
		udp_services.put(5683, "CoAP");
		udp_services.put(5900, "VNC");
		udp_services.put(6379, "Redis");
		udp_services.put(6667, "IRC");
		udp_services.put(7001, "WebLogic");
		udp_services.put(7002, "WebLogic SSL");
		udp_services.put(7070, "RealServer");
		udp_services.put(7443, "HTTPS Alt");
		udp_services.put(7777, "Game Servers");
		udp_services.put(8000, "HTTP Alt");
		udp_services.put(8008, "HTTP Alt");
		udp_services.put(8009, "AJP");
		udp_services.put(8080, "HTTP Proxy");
		udp_services.put(8081, "HTTP Alt");
		udp_services.put(8086, "InfluxDB");
		udp_services.put(8087, "Simplify Media");
		udp_services.put(8090, "HTTP Alt");
		udp_services.put(9000, "SonarQube");
		udp_services.put(9092, "Kafka");
		udp_services.put(9090, "Prometheus");
	}
	
	/**
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	public String detectTcpService(String ip, int port) {

		switch (port) {
		case 80:
		case 8080:
			return detectHttp(ip, port);

		case 22:
		case 21:
		case 25:
			return detectBanner(ip, port);

		default:
			return detectBanner(ip, port);
		}
	}
	
	/**
	 * 
	 * @param port
	 * @return
	 */
	public String detectUdpService(int port) {
		return udp_services.getOrDefault(port, "Unknown Service");
	}
	
	
	/**
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	private String detectHttp(String ip, int port) {
		try (Socket socket = new Socket(ip, port)) {

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println("HEAD / HTTP/1.1");
			out.println("Host: " + ip);
			out.println();
			out.flush();

			return in.readLine();

		} catch (IOException e) {
			return "HTTP (unknown)";
		}
	}
	
	/**
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	private String detectBanner(String ip, int port) {
		try (Socket socket = new Socket(ip, port)) {

			socket.setSoTimeout(2000);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String banner = in.readLine();

			return banner != null ? banner : "Unknown service";

		} catch (IOException e) {
			return "Unknown";
		}
	}
}
