package pgDev.bukkit.CommandPoints;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import pgDev.bukkit.CommandPoints.CommandPoints.EventType;

/**
 * Flat File Logger
 *
 * @author PG Dev Team (Devil Boy, Tux2, Nat was involved with this too!)
 */
public class FlatFileLogger extends TransLogger {
	
	PrintWriter out;
	
	public void FlatFileLogger() {
		
		try {
			FileWriter outFile = new FileWriter("./plugins/CommandPoints/PointEventLog.txt", true);
			PrintWriter out = new PrintWriter(outFile);
		} catch (IOException e) {
			System.out.println("Cannot write to the directory!");
		}
		
	}

	@Override
	public void logTransaction(EventType type, String player, double amount, String reason,
			String plugin) {
		
		if(type == EventType.GAIN) {
			out.println(player + " has recieved " + amount + " points from a transaction.");
		}
		if(type == EventType.LOSS) {
			out.println(player + " has lost " + amount + " points from a transaction.");
		}

	}
	
	@Override
	public void logCheck(EventType type, String player, String plugin) {
		
		out.println(player + "has checked how many points s/he has.");

	}

	@Override
	public void logMisc(String logOutput) {
		out.println(logOutput);
		
	}

	@Override
	public void logPointSet(EventType type, String player, double amount,
			String plugin) {
		
		out.println(player + " has had their points set to " + amount + " points.");
		
	}

}
