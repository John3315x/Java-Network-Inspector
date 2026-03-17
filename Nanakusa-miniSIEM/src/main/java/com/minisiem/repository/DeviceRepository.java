package com.minisiem.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.minisiem.database.DatabaseConnection;
import com.minisiem.model.Device;

public class DeviceRepository implements Controller {

	@Override
	public void create(Object object) {
		Device device = (Device) object;
		
		String sql = "INSERT INTO devices(ip_address, hostname, mac_address, seen_first_time)VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, device.getIp());
			stmt.setString(2, device.getHostname());
			stmt.setString(3, device.getMac());
			stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

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

}
