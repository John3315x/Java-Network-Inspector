package com.minisiem.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.minisiem.database.DatabaseConnection;
import com.minisiem.model.port.Port;

public class PortRepository implements Controller {
	
	private final int task_id;
	
	public PortRepository(int task_id) {
		this.task_id = task_id;
	}

	@Override
	public void create(Object object) {
		Port port = (Port) object;		
		
		String sql = "INSERT INTO ports(task_id, device_id, port_number, protocol, service)VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, task_id);
			stmt.setString(2, findDeviceIdByIp(port.getIpDevice()));
			stmt.setString(3, port.getPort());
			stmt.setString(4, port.getProtocol());
			stmt.setString(5, port.getService());

			stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Repository error- " + e.getMessage());
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
	        e.printStackTrace();
	    }

	    return null;
	}

}
