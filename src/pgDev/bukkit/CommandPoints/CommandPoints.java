package pgDev.bukkit.CommandPoints;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.entity.CreatureType;
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
 * @author PG Dev Team
 * @author Devil Boy
 */
public class CommandPoints extends JavaPlugin {
    private final CommandPointsPlayerListener playerListener = new CommandPointsPlayerListener(this);
    //private final CommandPointsBlockListener blockListener = new CommandPointsBlockListener(this);
    
    // Player Points Database
    private HashMap<String, Double> playerPoints = new HashMap<String, Double>();

    // File Locations
    String pluginMainDir = "./plugins/CommandPoints";
    String pluginConfigLocation = pluginMainDir + "/CommandPoints.cfg";
    String pointsDBLocation = pluginMainDir + "/playerPointsDB.dat";
    
    // Plugin Configuration
    Configuration pluginSettings;

    public void onEnable() {
    	// Check for the plugin directory (create if it does not exist)
    	File pluginDir = new File(pluginMainDir);
		if(!pluginDir.exists()) {
			boolean dirCreation = pluginDir.mkdirs();
			if (dirCreation) {
				System.out.println("New CommandPoints directory created!");
			}
		}
		
    	// Load the Configuration
    	try {
        	Properties preSettings = new Properties();
        	if ((new File(pluginConfigLocation)).exists()) {
        		preSettings.load(new FileInputStream(new File(pluginConfigLocation)));
        		pluginSettings = new Configuration(preSettings, this);
        		if (!pluginSettings.upToDate) {
        			pluginSettings.createConfig();
        			System.out.println("CommandPoints Configuration updated!");
        		}
        	} else {
        		pluginSettings = new Configuration(preSettings, this);
        		pluginSettings.createConfig();
        		System.out.println("CommandPoints Configuration created!");
        	}
        } catch (Exception e) {
        	System.out.println("Could not load configuration! " + e);
        }
        
        // Load the Points Database
        if ((new File(pointsDBLocation)).exists()) {
        	loadPointsDatabase();
        } else {
        	// Create the database file!
    		savePointsDatabase();
        }
    	
    	// Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);

        // Console output (Tells us we're alive)
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public void onDisable() {
        // Console output (Tells us we've died)
        System.out.println("CommandPoints disabled!");
    }
    
    
    // Plugin Developer API
    /* No Longer
    public CommandPointsAPI getAPI(JavaPlugin linkingPlugin) {
    	return new CommandPointsAPI(this, linkingPlugin);
    }*/
    
    
    // Database loading and saving
    
    protected void loadPointsDatabase() {

		// check for existing file
		File configFile = new File(pointsDBLocation);
		
		//if it exists, let's read it, if it doesn't, let's create it.
		if (configFile.exists()) {
			try {
				playerPoints.clear();
				Properties theprices = new Properties();
				theprices.load(new FileInputStream(configFile));
				Iterator<Entry<Object, Object>> iprices = theprices.entrySet().iterator();
				while(iprices.hasNext()) {
					Entry<Object, Object> price = iprices.next();
					try {
						playerPoints.put(price.getKey().toString().toLowerCase(), new Double(price.getValue().toString()));
					}catch (NumberFormatException ex) {
						System.out.println("[CommandPoints] Unable to parse the value for player " + price.getKey().toString());
					}
				}
			} catch (IOException e) {
				
			}
		}else {
			System.out.println("[CommandPoints] Points file not found, creating it.");
			savePointsDatabase();
		}
		
	}



	protected void savePointsDatabase() {
		try {
			BufferedWriter outChannel = new BufferedWriter(new FileWriter(pointsDBLocation));
			outChannel.write("# This contains all of the player's points. In normal circumstances\n" +
					"# this file should not be edited directly. Change their amounts in game.\n" +
					"\n" +
					"\n");
			 Set<Entry<String, Double>> ppoints = playerPoints.entrySet();
			for(Entry tpoints : ppoints) {
				outChannel.write(tpoints.getKey().toString() + " = " + String.valueOf(tpoints.getValue()) + "\n");
			}
			outChannel.close();
		} catch (Exception e) {
			System.out.println("[CommandPoints] ERRROR! Point file creation failed, points not saved to disk!");
		}
		
	}
    
    
    // Database Interaction Methods/Functions (Hooks)
    
    // Give a user points
    protected void hAddPoints(String playerName, double amount, String reason) {
    	if (playerPoints.containsKey(playerName)) {
    		playerPoints.put(playerName, new Double(playerPoints.get(playerName).doubleValue() + amount));
    	} else {
    		playerPoints.put(playerName, new Double(amount));
    	}
    }
    
    // Remove a user's points
    protected void hRemovePoints(String playerName, double amount, String reason) {
    	if (playerPoints.containsKey(playerName)) {
    		playerPoints.put(playerName, new Double(playerPoints.get(playerName).doubleValue() - amount));
    	}
    }
    
    // Output a user's number of points
    protected double hCheckPoints(String playerName) {
    	return playerPoints.get(playerName).doubleValue();
    }
    
    // Create a user account
    protected void hMakeAccount(String playerName) {
    	playerPoints.put(playerName, (double)0);
    }
    
    // Check if player has an account
    protected boolean hHasAccount(String playerName) {
    	return playerPoints.containsKey(playerName);
    }
    
    
    // Developer API Functions and Methods (API)
    
    /**
	 * Give points to a player
	 * @param playerName The player receiving the points
	 * @param amount How many points
	 * @param reason Why he's getting the points
	 */
	public void addPoints(String playerName, double amount, String reason) {
		hAddPoints(playerName, amount, reason);
	}
	
	/**
	 * Remove points from a player
	 * @param playerName The player losing the points
	 * @param amount How many points
	 * @param reason Why he's losing the points
	 */
	public void removePoints(String playerName, double amount, String reason) {
		hRemovePoints(playerName, amount, reason);
	}
	
	/**
	 * Get how many points a player has
	 * @param playerName The player we want to find about
	 * @return How many points the player has
	 */
	public double getPoints(String playerName) {
		return hCheckPoints(playerName);
	}
	
}

