package com.minisiem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/nanakusa_minisiem";
    private static final String USER = "devuser";
    private static final String PASSWORD = "aduf#3=845g9/";
    
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }
}
