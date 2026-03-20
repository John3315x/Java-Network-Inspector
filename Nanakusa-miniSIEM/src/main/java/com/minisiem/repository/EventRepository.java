package com.minisiem.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.minisiem.database.DatabaseConnection;
import com.minisiem.model.Event;

public class EventRepository implements Controller {

	@Override
	public void create(Object object) {
		Event event = (Event) object;

		String sql = "INSERT INTO events(code, type, title, description, event_date)VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, event.getCode());
			stmt.setString(2, event.getType().name());
			stmt.setString(3, event.getTitle());
			stmt.setString(4, event.getDescription());
			stmt.setTimestamp(5, Timestamp.valueOf(event.getDateTime()));

			stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Repository error- [Evenet] " + e.getMessage());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getItems(String param1, String param2) {
		// TODO Auto-generated method stub
		return null;
	}

}
