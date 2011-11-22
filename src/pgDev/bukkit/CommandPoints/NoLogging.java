package pgDev.bukkit.CommandPoints;

import pgDev.bukkit.CommandPoints.CommandPoints.EventType;

/**
 * Empty Logger (No Logging)
 *
 * @author PG Dev Team (Devil Boy)
 */

public class NoLogging extends TransLogger{
	
	public NoLogging() {
		
	}

	@Override
	public void logTransaction(EventType type, String player, double amount,
			String reason, String plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logPointSet(EventType type, String player, double amount,
			String plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logCheck(EventType type, String player, String plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logMisc(String logOutput) {
		// TODO Auto-generated method stub
		
	}

}
