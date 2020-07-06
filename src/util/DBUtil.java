package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	private static Connection connection;

	private static final String URL = "jdbc:postgresql://localhost/supermarket";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";

	static {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Exeception during connection");
		}
	} 
	
	public static PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}
	
	public static PreparedStatement prepareStatementAndGetGeneratedKeys(String sql) throws SQLException {
		return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}
	
	public static Statement createStatement() throws SQLException {
		return connection.createStatement();
	}


	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
