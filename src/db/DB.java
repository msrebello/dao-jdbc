package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	// CONNECTION WITH DATABASE STARTS NULL:

	private static Connection conn = null;

	// AUXILIARY METHODS FOR CONNECTION TESTS:

	public static Connection getConnection() {

		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);

			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}

		return conn;
	}

	public static void closeConnection() {

		if (conn != null) {
			try {
				conn.close();

			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	// LOADING DATABASE PROPERTIES:

	private static Properties loadProperties() {

		try (FileInputStream file = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(file);

			return props;
		}

		catch (IOException e) {

			throw new DbException(e.getMessage());
		}

	}
	
	// CLOSE EXTERNAL CONNECTIONS

	public static void closeStatement(Statement statement) {
		if (statement != null) {

			try {
				statement.close();

			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResulSet(ResultSet resultSet) {
		if (resultSet != null) {

			try {
				resultSet.close();

			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}

		}
	}

}
