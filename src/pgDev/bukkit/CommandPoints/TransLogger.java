package pgDev.bukkit.CommandPoints;

public abstract class TransLogger {
	
	public abstract void logTransaction(String type, double amount, String reason, String plugin);

}
