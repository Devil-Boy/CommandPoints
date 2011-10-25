package pgDev.bukkit.CommandPoints;

import org.bukkit.plugin.java.JavaPlugin;

// Note for Devil from Nat: I don't think this is the best way to implement an API maybe a plugin like system is better?
// Simply write an abstract plugin class and have the linking bukkit plugins put their write up in the CommandPoints folder? You decide
// Also, should I just be making (some) of the functions in CommandPoints accessible via the API or do I have this all wrong?

/**
 * CommandPointsAPI
 * @author Nat
 * @author PG Dev Team
 *
 */
public class CommandPointsAPI {
	
	private JavaPlugin linkingPlugin;
	private CommandPoints commandPoints;
	
	/**
	 * Constructor for the CommandPointsAPI class
	 * @param newCommandPoints The current instance of the CommandPoints plugin
	 * @param newLinkingPlugin The plugin we want to link.
	 *
	 */
	public CommandPointsAPI(CommandPoints newCommandPoints, JavaPlugin newLinkingPlugin) {
		
		commandPoints = newCommandPoints;
		linkingPlugin = newLinkingPlugin;
		
	}
	
	/**
	 * Give points to a player
	 * @param playerName The player receiving the points
	 * @param amount How many points
	 * @param reason Why he's getting the points
	 */
	public void addPoints(String playerName, int amount, String reason) {
		commandPoints.addPoints(playerName, amount, reason);
	}
	
	/**
	 * Remove points from a player
	 * @param playerName The player losing the points
	 * @param amount How many points
	 * @param reason Why he's losing the points
	 */
	public void removePoints(String playerName, int amount, String reason) {
		commandPoints.removePoints(playerName, amount, reason);
	}
	
	/**
	 * Get how many points a player has
	 * @param playerName The player we want to find about
	 * @return How many points the player has
	 */
	public int getPoints(String playerName) {
		return commandPoints.checkPoints(playerName);
	}
	
	


}
