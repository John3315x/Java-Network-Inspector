package com.minisiem.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.minisiem.database.DatabaseConnection;
import com.minisiem.model.port.Port;

public class PortRepository implements Controller {

	private int task_id;

	public PortRepository(int task_id) {
		this.task_id = task_id;
	}

	public PortRepository() {

	}

	@Override
	public void create(Object object) {
		Port port = (Port) object;

		String sql = "INSERT INTO port_scan_history (task_id, device_id, port_number, protocol, service)VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, task_id);
			stmt.setString(2, findDeviceIdByIp(port.getIpDevice()));
			stmt.setString(3, port.getPort_number());
			stmt.setString(4, port.getProtocol());
			stmt.setString(5, port.getService());

			stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Repository error- [Port] " + e.getMessage());
		}
	}

	@Override
	public void update(Object object, int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Object> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getItems(String param1, int param2) {
		String sql = "SELECT * FROM port_scan_history WHERE " + param1 + " = ?";
		List<Object> list = new ArrayList<Object>();

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, param2);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {

				Port port = new Port();

				port.setTask_id(rs.getInt("task_id"));
				port.setIpDevice(rs.getString("device_id"));
				port.setPort_number(rs.getString("port_number"));
				port.setProtocol(rs.getString("protocol"));
				port.setService(rs.getString("service"));

				list.add(port);
			}

		} catch (SQLException e) {
			System.out.println("Repository error- [Port] " + e.getMessage());
		}

		return list;
	}

	@Override
	public List<Object> getItems(String param1, String param2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param ip
	 * @return
	 */
	public String findDeviceIdByIp(String ip) {

		String sql = "SELECT id FROM devices WHERE ip_address = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, ip);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("id") + "";
			}

		} catch (SQLException e) {
			System.out.println("Repository error- [Port] " + e.getMessage());
		}

		return null;
	}

}
