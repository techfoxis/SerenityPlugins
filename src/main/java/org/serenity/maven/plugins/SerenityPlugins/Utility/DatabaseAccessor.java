package org.serenity.maven.plugins.SerenityPlugins.Utility;

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
	
	private static DatabaseAccessor instance = null;
	private DatabaseAccessor() {
		JavaPlugin plugin = JavaPlugin.getPlugin(SerenityPlugins.class);
		
		this.logger = plugin.getLogger();
	}
	
	public static DatabaseAccessor get() {
		if (instance == null) {
			instance = new DatabaseAccessor();
		}
		
		return instance;
	}
	
	// TODO Test
	private Connection establishConnection(String url, String user, String password) {
		Connection connection;
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException error) {
			
			logger.log(Level.WARNING, "Database Connection failed... \n" + error.getMessage(), error);
			connection = null;
		}
		
		return connection;
	}
	
	// TODO Test
	private Statement createStatement(Connection connection) {
		Statement statement;
		
		try {
			statement = connection.createStatement();
			
		} catch (SQLException error) {
			
			logger.log(Level.WARNING, "Database Statement Creation Failed...\n" + error.getMessage(), error);
			statement = null;
		}
		
		return statement;
	}
	
	public void setAuthinticationDetails(String url, String user, String password) {
		this.databaseUrl = url;
		this.databaseUser = user;
		this.databasePassword = password;
	}
	
	// TODO Test
	public ResultSet query(String query) {
		Connection connection;
		Statement statement;
		ResultSet results;
		
		try {
			connection = establishConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
			statement = createStatement(connection);
			results = statement.executeQuery(query);
		
		} catch (SQLException error) {
			
			logger.log(Level.WARNING, "Database Query Failed...\n" + error.getMessage(), error);
			results = null;
		}
		
		return results;
	}
}
