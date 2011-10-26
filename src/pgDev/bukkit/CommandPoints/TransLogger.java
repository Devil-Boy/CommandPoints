package pgDev.bukkit.CommandPoints;

import pgDev.bukkit.CommandPoints.CommandPoints.EventType;

public abstract class TransLogger {
	
	public abstract void logTransaction(EventType type, String player, double amount, String reason, String plugin);

	public abstract void logCheck(EventType type, String player, String plugin);
	
	public abstract void logMisc(String logOutput);

}
