package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static Connection instance;

	private static final String URL = "jdbc:postgresql://localhost/supermarket";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";

	
	public static Connection connect() throws SQLException {

		if (instance == null) {
			instance = DriverManager.getConnection(URL, USER, PASSWORD);
		}
		return instance;
	}

	
	public static void close () throws SQLException {
		
		if (instance!=null) {
			
			instance.close();
		}
	}	
}
