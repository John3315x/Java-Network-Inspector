package com.minisiem.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
			stmt.setTimestamp(4, Timestamp.valueOf(device.getSeenFirstTime()));

			stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Repository error- [Device] " + e.getMessage());
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
		List<Object> devices = new ArrayList<>();
		String sql = "SELECT * FROM devices";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				Device device = new Device();

				device.setId(rs.getInt("id"));
				device.setIp(rs.getString("ip_address"));
				device.setHostname(rs.getString("hostname"));
				device.setMac(rs.getString("mac_address"));

				device.setSeenFirstTime(rs.getObject("seen_first_time", LocalDateTime.class));

				devices.add(device);
			}

		} catch (SQLException e) {
			System.out.println("Repository error- [Device] " + e.getMessage());
		}

		return devices;
	}

	@Override
	public List<Object> getItems(String param1, int param2) {
		List<Object> devices = new ArrayList<>();
		String sql = "SELECT * FROM devices WHERE " + param1 + " = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, param2);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Device device = new Device();

				device.setId(rs.getInt("id"));
				device.setIp(rs.getString("ip_address"));
				device.setHostname(rs.getString("hostname"));
				device.setMac(rs.getString("mac_address"));

				device.setSeenFirstTime(rs.getObject("seen_first_time", LocalDateTime.class));

				devices.add(device);
			}

		} catch (SQLException e) {
			System.out.println("Repository error- [Device] " + e.getMessage());
		}

		return devices;
	}

	@Override
	public List<Object> getItems(String param1, String param2) {
		List<Object> devices = new ArrayList<>();
		String sql = "SELECT * FROM devices WHERE " + param1 + " = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, param2);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Device device = new Device();

				device.setId(rs.getInt("id"));
				device.setIp(rs.getString("ip_address"));
				device.setHostname(rs.getString("hostname"));
				device.setMac(rs.getString("mac_address"));

				device.setSeenFirstTime(rs.getObject("seen_first_time", LocalDateTime.class));

				devices.add(device);
			}

		} catch (SQLException e) {
			System.out.println("Repository error- [Device] " + e.getMessage());
		}

		return devices;
	}
}
