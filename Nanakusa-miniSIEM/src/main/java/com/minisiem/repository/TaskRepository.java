package com.minisiem.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.minisiem.database.DatabaseConnection;
import com.minisiem.model.Task;

public class TaskRepository implements Controller {

	public int createAndGetId(Task task) {

		int id = -1;

		String sql = "INSERT INTO task(code, type, title, description, status, task_date)VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, task.getCode());
			stmt.setString(2, task.getType().name());
			stmt.setString(3, task.getTitle());
			stmt.setString(4, task.getDescription());
			stmt.setString(5, task.getStatus().name());
			stmt.setTimestamp(6, Timestamp.valueOf(task.getDateTime()));

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt(1);
				// System.out.println("ID generado: " + id);
				return id;
			}

		} catch (SQLException e) {
			System.out.println("Repository error- " + e.getMessage());
		}

		return id;
	}

	@Override
	public void create(Object object) {
		// TODO Auto-generated method stub
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
