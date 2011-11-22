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
	
	// Toggle for failure
	boolean failure = false;

	public FlatFileLogger(String directory) {
		
		try {
			FileWriter outFile = new FileWriter(directory, true);
			out = new PrintWriter(outFile);
		} catch (IOException e) {
			failure = true;
			System.out.println("Cannot write to the directory! CommandPoints will continue running without logging.");
		}
		
	}

	@Override
	public void logTransaction(EventType type, String player, double amount, String reason, String plugin) {
		if (failure) return; // Allows us to continue without nullpointer
		
		if(type == EventType.GAIN) {
			out.println(player + " has recieved " + amount + " points from a transaction.");
		}
		if(type == EventType.LOSS) {
			out.println(player + " has lost " + amount + " points from a transaction.");
		}

	}
	
	@Override
	public void logCheck(EventType type, String player, String plugin) {
		if (failure) return; // Allows us to continue without nullpointer
		
		out.println(player + "has checked how many points s/he has.");
	}

	@Override
	public void logMisc(String logOutput) {
		if (failure) return; // Allows us to continue without nullpointer
		
		out.println(logOutput);
	}

	@Override
	public void logPointSet(EventType type, String player, double amount, String plugin) {
		if (failure) return; // Allows us to continue without nullpointer
		
		out.println(player + " has had their points set to " + amount + " points.");
	}

}
