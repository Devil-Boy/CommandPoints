package pgDev.bukkit.CommandPoints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Properties;

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
    private HashMap<String, Integer> playerPoints = new HashMap<String, Integer>();

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
        	playerPoints = loadPointsDatabase();
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
    public CommandPointsAPI getAPI(JavaPlugin linkingPlugin) {
    	return new CommandPointsAPI(this, linkingPlugin);
    }
    
    
    // Database loading and saving
    
    // Load Points Database
    protected HashMap<String, Integer> loadPointsDatabase() {
    	try{
    		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pointsDBLocation));
    		Object result = ois.readObject();
    		//you can feel free to cast result to HashMap<Player,Boolean> if you know there's that HashMap in the file
    		return (HashMap<String, Integer>)result;
    	} catch(Exception e){
    		e.printStackTrace();
    	}
		return playerPoints;
    }
    
    // Save Points Database
    protected void savePointsDatabase() {
    	try{
    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pointsDBLocation));
    		oos.writeObject(playerPoints);
    		oos.flush();
    		oos.close();
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    // Database Interaction Methods/Functions
    
    // Give a user points
    protected void addPoints(String playerName, int amount, String reason) {
    	if (playerPoints.containsKey(playerName)) {
    		playerPoints.put(playerName, playerPoints.get(playerName) + amount);
    	} else {
    		playerPoints.put(playerName, amount);
    	}
    }
    
    // Remove a user's points
    protected void removePoints(String playerName, int amount, String reason) {
    	if (playerPoints.containsKey(playerName)) {
    		playerPoints.put(playerName, playerPoints.get(playerName) - amount);
    	}
    }
    
    // Output a user's number of points
    protected int checkPoints(String playerName) {
    	return playerPoints.get(playerName);
    }
    
    // Create a user account
    protected void makeAccount(String playerName) {
    	playerPoints.put(playerName, 0);
    }
    
    // Check if player has an account
    protected boolean hasAccount(String playerName) {
    	return playerPoints.containsKey(playerName);
    }
}

