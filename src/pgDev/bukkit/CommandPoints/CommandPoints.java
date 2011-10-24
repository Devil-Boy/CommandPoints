package pgDev.bukkit.CommandPoints;

import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * CommandPoints for Bukkit
 *
 * @author DevilBoy
 */
public class CommandPoints extends JavaPlugin {
    private final CommandPointsPlayerListener playerListener = new CommandPointsPlayerListener(this);
    private final CommandPointsBlockListener blockListener = new CommandPointsBlockListener(this);
    
    // Player Points Database
    private HashMap<String, Integer> playerPoints = new HashMap<String, Integer>();

    // File Locations
    String pluginMainDir = "./plugins/CommandPoints";
    String pluginConfigLocation = pluginMainDir + "/CommandPoints.cfg";
    
    // Plugin Configuration
    Configuration pluginSettings;

    public void onEnable() {
    	// Register our events
        PluginManager pm = getServer().getPluginManager();
       

        // Console output (Tells us we're alive)
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {
        // Console output (Tells us we've died)
        System.out.println("CommandPoints disabled!");
    }
    
    
    // Database Interaction Methods/Functions
    
    // Give a user points
    public void addPoints(String playerName, int amount, String reason) {
    	if (playerPoints.containsKey(playerName)) {
    		playerPoints.put(playerName, playerPoints.get(playerName) + amount);
    	} else {
    		playerPoints.put(playerName, amount);
    	}
    }
    
    // Remove a user's points
    public void removePoints(String playerName, int amount, String reason) {
    	if (playerPoints.containsKey(playerName)) {
    		playerPoints.put(playerName, playerPoints.get(playerName) - amount);
    	}
    }
    
    // Output a user's number of points
    public int checkPoints(String playerName) {
    	return playerPoints.get(playerName);
    }
}

