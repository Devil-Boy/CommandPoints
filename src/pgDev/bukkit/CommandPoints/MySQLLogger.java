package pgDev.bukkit.CommandPoints;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import pgDev.bukkit.CommandPoints.CommandPoints.EventType;

/**
 * MySQL Database Logger
 *
 * @author PG Dev Team (Devil Boy)
 */
public class MySQLLogger extends TransLogger {
	CommandPoints plugin;
	
	public MySQLLogger(CommandPoints pluginI) {
		plugin = pluginI;
		
		// Get the MySQL Connect Driver
		Statement stmt;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:url here", "user here", "password here");
			stmt = con.createStatement();
			stmt.executeUpdate("CREATE DATABASE <dbname>"); //Database is created here (probably not necessary as we want the user to create the DB)
			con.close();
		} catch(ClassNotFoundException e) {
			
		} catch (SQLException e) {
			
		}
	}
	
	public void logTransaction(EventType type, String player, double amount, String reason, String plugin) {
		
	}

	public void logPointSet(EventType type, String player, double amount, String plugin) {
		
	}

	public void logCheck(EventType type, String player, String plugin) {
		
	}

	public void logMisc(String logOutput) {
		
	}

}
