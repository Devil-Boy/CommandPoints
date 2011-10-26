package pgDev.bukkit.CommandPoints;

import org.bukkit.plugin.Plugin;

/**
 * CommandPoints API
 *
 * @author PG Dev Team (Devil Boy, Nat)
 */
public class CommandPointsAPI {
	CommandPoints cp;
	
	/**
	 * CommandPointsAPI Constructor
	 * @param playerName The player receiving the points
	 * @param amount How many points
	 * @param reason Why he's getting the points
	 */
	public CommandPointsAPI(CommandPoints pluginI) {
		cp = pluginI;
	}
	
	
	// Developer API Functions and Methods (API)
    
    /**
	 * Give points to a player
	 * @param playerName The player receiving the points
	 * @param amount How many points
	 * @param reason Why he's getting the points
	 */
	public void addPoints(String playerName, double amount, String reason, Plugin plugin) {
		cp.addPoints(playerName, amount, reason, plugin);
	}
	
	/**
	 * Remove points from a player
	 * @param playerName The player losing the points
	 * @param amount How many points
	 * @param reason Why he's losing the points
	 */
	public void removePoints(String playerName, double amount, String reason, Plugin plugin) {
		cp.removePoints(playerName, amount, reason, plugin);
	}
	
	/**
	 * Get how many points a player has
	 * @param playerName The player we want to find about
	 * @return How many points the player has
	 */
	public double getPoints(String playerName, Plugin plugin) {
		return cp.checkPoints(playerName, plugin);
	}

}
