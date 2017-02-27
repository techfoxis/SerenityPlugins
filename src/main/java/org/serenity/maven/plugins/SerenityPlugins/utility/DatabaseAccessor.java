package org.serenity.maven.plugins.SerenityPlugins.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.serenity.maven.plugins.SerenityPlugins.SerenityPlugins;

public class DatabaseAccessor {

	private Logger logger = null;
	
	private String databaseUrl = null;
	private String databaseUser = null;
	private String databasePassword = null;
	
	public DatabaseAccessor(String url, String user, String password) {
		JavaPlugin plugin = JavaPlugin.getPlugin(SerenityPlugins.class);
		this.databaseUrl = url;
		this.databaseUser = user;
		this.databasePassword = password;
		this.logger = plugin.getLogger();
	}
	
	private Connection establishConnection(String url, String user, String password) {
		Connection connection;
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException error) {
			
			logger.log(Level.WARNING, "Database Connection Creation Failed... \n" + error.toString(), error);
			connection = null;
		}
		
		return connection;
	}
	
	private Statement createStatement(Connection connection) {
		Statement statement;
		
		try {
			statement = connection.createStatement();
			
		} catch (SQLException error) {
			
			logger.log(Level.WARNING, "Database Statement Creation Failed...\n" + error.toString(), error);
			statement = null;
		}
		
		return statement;
	}
	
	public ResultSet query(String query) throws SQLException {
		Connection connection;
		Statement statement;
		ResultSet results;

		connection = establishConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
		statement = createStatement(connection);
		results = statement.executeQuery(query);
		
		return results;
	}
	
	public int update(String update) throws SQLException {
		Connection connection;
		Statement statement;
		int result;
		
		connection = establishConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
		statement = createStatement(connection);
		result = statement.executeUpdate(update);
		
		return result;
	}
}
