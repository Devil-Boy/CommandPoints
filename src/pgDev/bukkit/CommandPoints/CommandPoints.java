package pgDev.bukkit.CommandPoints;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * CommandPoints for Bukkit
 *
 * @author PG Dev Team (Devil Boy, Tux2)
 */
public class CommandPoints extends JavaPlugin {
	// Listeners
    private final CommandPointsPlayerListener playerListener = new CommandPointsPlayerListener(this);
    private final CommandListener commandListener = new CommandListener(this);
    
    // Permissions Integration
    private static PermissionHandler Permissions;
    
    // Player Points Database
    protected HashMap<String, Integer> playerPoints = new HashMap<String, Integer>();

    // File Locations
    String pluginMainDir = "./plugins/CommandPoints";
    String pluginConfigLocation = pluginMainDir + "/CommandPoints.cfg";
    String pointsDBLocation = pluginMainDir + "/PlayerPointsDB.ini";
    String pointEventLogLocation = pluginMainDir + "/PointEventLog.txt";
    
    // Abstract Logger and Enums
    protected TransLogger thelogger;
    
    public enum EventType {
    	GAIN, LOSS, CHECK, NEWACCOUNT, POINTSET
    }
    
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
        	System.out.println("Could not load CommandPoints configuration! " + e);
        }
        
        // Load the Points Database
        if ((new File(pointsDBLocation)).exists()) {
        	loadPointsDatabase();
        } else {
        	// Create the database file!
    		savePointsDatabase();
        }
        
        thelogger = new FlatFileLogger();
    	
    	// Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        
        // Send commands to the executor
        PluginCommand fullCommand = this.getCommand("commandpoints");
        fullCommand.setExecutor(commandListener);
        try {
	        PluginCommand shortCommand = this.getCommand("cp");
	        shortCommand.setExecutor(commandListener);
	        //((PluginCommand)this.getCommand("cp")).setExecutor(commandListener); Made this line for fun, probably shouldn't be used though.
        }catch (NullPointerException e) {
        	System.out.println("CommandPoints: Another plugin is using /cp, please use /commandpoints");
        }
        
        // Permissions turn on!
    	setupPermissions();

        // Console output (Tells us we're alive)
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public void onDisable() {
        // Console output (Tells us we've died)
        System.out.println("CommandPoints disabled!");
    }
    
    // Permissions Methods
    private void setupPermissions() {
        Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
            if (permissions != null) {
                Permissions = ((Permissions)permissions).getHandler();
            } else {
            }
        }
    }
    
    protected boolean hasPermissions(Player player, String node) {
        if (Permissions != null) {
        	return Permissions.has(player, node);
        } else {
            return player.hasPermission(node);
        }
    }
    
    
    // Plugin Developer API
    /**
     * Obtain the API
     *
     * @return The CommandPoints Plugin Developer API
     */
    CommandPointsAPI theAPI = new CommandPointsAPI(this);
    public CommandPointsAPI getAPI() {
    	return theAPI;
    }
    
    
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
						playerPoints.put(price.getKey().toString().toLowerCase(), new Integer(price.getValue().toString()));
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
			 Set<Entry<String, Integer>> ppoints = playerPoints.entrySet();
			for(Entry<String, Integer> tpoints : ppoints) {
				outChannel.write(tpoints.getKey().toString() + " = " + String.valueOf(tpoints.getValue()) + "\n");
			}
			outChannel.close();
		} catch (Exception e) {
			System.out.println("[CommandPoints] ERRROR! Point file creation failed, points not saved to disk!");
		}
		
	}
    
	
    
    // Database Interaction Methods/Functions (Hooks)
    
    // Give a user points
    protected void addPoints(String playerName, int amount, String reason, Plugin plugin) {
    	if (playerPoints.containsKey(playerName.toLowerCase())) {
    		playerPoints.put(playerName.toLowerCase(), playerPoints.get(playerName.toLowerCase()) + amount);
    	} else {
    		playerPoints.put(playerName.toLowerCase(), amount);
    	}
    	
    	// Save database
    	if (!pluginSettings.reduceOverhead) {
    		savePointsDatabase();
    	}
    	
    	// To the logger!
    	if (pluginSettings.logEvents.contains("gain")) {
    		thelogger.logTransaction(EventType.GAIN, playerName, amount, reason, plugin.getDescription().getName());
    	}
    }
    
    // Give all users points
    protected void addPointsAll(int amount, String reason, Plugin plugin) {
    	Set<String> players = playerPoints.keySet();
    	for (String player : players) {
    		addPoints(player, amount, reason, plugin);
    	}
    }
    
    // Transfer points
    protected void transferPoints(String giver, String receiver, int amount, String reason, Plugin plugin) {
    	removePoints(giver, amount, reason, plugin);
    	addPoints(receiver, amount, reason, plugin);
    }
    
    // Remove a user's points
    protected void removePoints(String playerName, int amount, String reason, Plugin plugin) {
    	if (playerPoints.containsKey(playerName.toLowerCase())) {
    		playerPoints.put(playerName.toLowerCase(), playerPoints.get(playerName.toLowerCase()) - amount);
    	}
    	
    	// Save database
    	if (!pluginSettings.reduceOverhead) {
    		savePointsDatabase();
    	}
    	
    	// To the logger!
    	if (pluginSettings.logEvents.contains("gain")) {
    		thelogger.logTransaction(EventType.LOSS, playerName, amount, reason, plugin.getDescription().getName());
    	}
    }
    
    // Remove points from all users
    protected void removePointsAll(int amount, String reason, Plugin plugin) {
    	Set<String> players = playerPoints.keySet();
    	for (String player : players) {
    		removePoints(player, amount, reason, plugin);
    	}
    }
    
    // Set a user's points
    protected void setPoints(String playerName, int amount, Plugin plugin) {
    	if (playerPoints.containsKey(playerName.toLowerCase())) {
    		playerPoints.put(playerName.toLowerCase(), amount);
    	}
    	
    	// Save database
    	if (!pluginSettings.reduceOverhead) {
    		savePointsDatabase();
    	}
    	
    	// To the logger!
    	if (pluginSettings.logEvents.contains("set")) {
    		thelogger.logPointSet(EventType.POINTSET, playerName, amount, plugin.getDescription().getName());
    	}
    }
    
    // Output a user's number of points
    protected int checkPoints(String playerName, Plugin plugin) {
    	// To the logger!
    	if (pluginSettings.logEvents.contains("check")) {
    		thelogger.logCheck(EventType.CHECK, playerName, plugin.getDescription().getName());
    	}
    	
    	return playerPoints.get(playerName.toLowerCase());
    }
    
    // Check if the user's account contains at least a certain number of points
    protected boolean hasPoints(String playerName, int amount) {
    	if (playerPoints.get(playerName.toLowerCase()) >= amount) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    // Create a user account
    protected void makeAccount(String playerName, Plugin plugin) {
    	playerPoints.put(playerName.toLowerCase(), 0);
    	
    	// Save database
    	if (!pluginSettings.reduceOverhead) {
    		savePointsDatabase();
    	}
    	
    	// To the logger!
    	if (pluginSettings.logEvents.contains("newaccount")) {
    		thelogger.logCheck(EventType.NEWACCOUNT, playerName, plugin.getDescription().getName());
    	}
    }
    
    // Check if player has an account
    protected boolean hasAccount(String playerName) {
    	return playerPoints.containsKey(playerName.toLowerCase());
    }
    
    // Clear all points
    protected void clearPoints() {
    	playerPoints.clear();
    	
    	// Save database
    	if (!pluginSettings.reduceOverhead) {
    		savePointsDatabase();
    	}
    	
    	// To the logger!
    	thelogger.logMisc("Points database cleared!");
    }
	
}

