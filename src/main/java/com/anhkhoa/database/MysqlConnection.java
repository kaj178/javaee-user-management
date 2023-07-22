package com.anhkhoa.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
	private static String driver = "com.mysql.cj.jdbc.Driver";
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		String DB_HOST = "localhost:3306";
		String DB_NAME = "public-api";
		String DB_USERNAME = "root";
		String DB_PASSWORD = "";
		String DB_URL = String.format("jdbc:mysql://%s/%s", DB_HOST, DB_NAME);
		
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("Connect successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: Connection failed! - " + e.getMessage());
		}
		return connection;
	}
}
