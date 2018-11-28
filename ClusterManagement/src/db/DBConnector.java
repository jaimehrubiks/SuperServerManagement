package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * This class serves as a wrapper object for creating the connection to the database.
 */
public class DBConnector {

	// Code database URL
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/superservermanagement?autoReconnect=true&useSSL=false";

	// Database credentials
	static final String USER = "itmd510", PASS = "1234";

	/*
	 * This method is the one that makes the actual connection to the database and returns the connection object
	 */
	public Connection connect() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

}
