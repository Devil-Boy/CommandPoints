package pgDev.bukkit.CommandPoints;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import pgDev.bukkit.CommandPoints.CommandPoints.EventType;

/**
 * Flat File Logger
 *
 * @author PG Dev Team (Devil Boy, Tux2, Nat was involved with this too!)
 */
public class FlatFileLogger extends TransLogger {
	
	private Logger logger = Logger.getLogger("CommandPoints");
	
	// Toggle for failure
	boolean failure = false;

	public FlatFileLogger(String directory) {

		
		
		try {
			logger.setLevel(Level.FINEST);
		    FileHandler fileTxt = new FileHandler(directory, true);
		    fileTxt.setFormatter(new CPLogFormatter());
		    logger.addHandler(fileTxt);
		    logger.setUseParentHandlers(false);
		} catch (IOException e) {
			failure = true;
			System.out.println("Cannot write to the directory! CommandPoints will continue running without logging.");
		}
		
	}

	@Override
	public void logTransaction(EventType type, String player, double amount, String reason, String plugin) {
		if (failure) return; // Allows us to continue without nullpointer
		
		if(type == EventType.GAIN) {
			logger.info(player + " has recieved " + amount + " points. Reason: " + reason);
		}
		if(type == EventType.LOSS) {
			logger.info(player + " has lost " + amount + " points. Reason: " + reason);
		}

	}
	
	@Override
	public void logCheck(EventType type, String player, String plugin) {
		if (failure) return; // Allows us to continue without nullpointer
		
		logger.info(player + "has checked how many points s/he has.");
	}

	@Override
	public void logMisc(String logOutput) {
		if (failure) return; // Allows us to continue without nullpointer
		
		logger.info(logOutput);
	}

	@Override
	public void logPointSet(EventType type, String player, double amount, String plugin) {
		if (failure) return; // Allows us to continue without nullpointer
		
		logger.info(player + " has had their points set to " + amount + " points.");
	}

}
