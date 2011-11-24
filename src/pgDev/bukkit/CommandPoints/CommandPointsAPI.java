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
	 * @param pluginI The CommandPoints plugin on the server
	 */
	protected CommandPointsAPI(CommandPoints pluginI) {
		cp = pluginI;
	}
	
	
	// Developer API Functions and Methods (API)
    
    /**
	 * Give points to a player
	 * @param playerName The player receiving the points
	 * @param amount How many points
	 * @param reason Why he's getting the points
	 * @param plugin The plugin running this method
	 */
	public void addPoints(String playerName, int amount, String reason, Plugin plugin) {
		cp.addPoints(playerName, amount, reason, plugin);
	}
	
	/**
	 * Remove points from a player
	 * @param playerName The player losing the points
	 * @param amount How many points
	 * @param reason Why he's losing the points
	 * @param plugin The plugin running this method
	 */
	public void removePoints(String playerName, int amount, String reason, Plugin plugin) {
		cp.removePoints(playerName, amount, reason, plugin);
		
		// Check for negative points
		if (cp.playerPoints.get(playerName.toLowerCase()) < 0) {
			cp.setPoints(playerName, 0, plugin);
		}
	}
	
	/**
	 * Remove points from a player without checking if their number of points goes below zero
	 * @param playerName The player losing the points
	 * @param amount How many points
	 * @param reason Why he's losing the points
	 * @param plugin The plugin running this method
	 */
	@Deprecated
	public void removePointsNoCheck(String playerName, int amount, String reason, Plugin plugin) {
		cp.removePoints(playerName, amount, reason, plugin);
	}
	
	/**
	 * Set the amount of points a player has
	 * @param playerName The player getting his points set
	 * @param amount How many points
	 * @param plugin The plugin running this method
	 */
	public void setPoints(String playerName, int amount, Plugin plugin) {
		cp.setPoints(playerName, amount, plugin);
	}
	
	/**
	 * Get how many points a player has
	 * @param playerName The player we want to find about
	 * @param plugin The plugin running this function
	 * @return How many points the player has
	 */
	public int getPoints(String playerName, Plugin plugin) {
		return cp.checkPoints(playerName, plugin);
	}
	
	/**
	 * Check if the given player has an account
	 * @param playerName The player we want to find about
	 * @param plugin The plugin running this function
	 * @return Whether or not the player has an account
	 */
	public boolean hasAccount(String playerName, Plugin plugin) {
		return cp.hasAccount(playerName);
	}
	
	/**
	 * Check if the given player has at least the given amount of points
	 * @param playerName The player we want to find about
	 * @param amount The amount of points we're checking for
	 * @param plugin The plugin running this function
	 * @return Whether or not the player has at least that many points
	 */
	public boolean hasPoints(String playerName, int amount, Plugin plugin) {
		return cp.hasPoints(playerName, amount);
	}

}
