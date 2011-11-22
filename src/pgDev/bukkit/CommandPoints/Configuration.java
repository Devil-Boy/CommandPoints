package pgDev.bukkit.CommandPoints;


import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * CommandPoints Configuration
 *
 * @author PG Dev Team (Devil Boy)
 */
public class Configuration {
	private Properties properties;
	private final CommandPoints plugin;
	public boolean upToDate = true;
	
	// List of Config Options
	boolean gainlossConsoleOutput;
	String logEvents;
	boolean reduceOverhead;
	boolean receiveNotify;
	
	public Configuration(Properties p, final CommandPoints plugin) {
        properties = p;
        this.plugin = plugin;
        
        // Grab values here.
        gainlossConsoleOutput = getBoolean("gainlossConsoleOutput", false);
        logEvents = getString("logEvents", "gain loss");
        reduceOverhead = getBoolean("reduceOverhead", false);
        receiveNotify = getBoolean("receiveNotify", true);
	}
	
	// Value obtaining functions down below
	public int getInt(String label, int thedefault) {
		String value;
        try {
        	value = getString(label);
        	return Integer.parseInt(value);
        } catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public double getDouble(String label) throws NoSuchElementException {
        String value = getString(label);
        return Double.parseDouble(value);
    }
    
    public File getFile(String label) throws NoSuchElementException {
        String value = getString(label);
        return new File(value);
    }

    public boolean getBoolean(String label, boolean thedefault) {
    	String values;
        try {
        	values = getString(label);
        	return Boolean.valueOf(values).booleanValue();
        } catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public Color getColor(String label) {
        String value = getString(label);
        Color color = Color.decode(value);
        return color;
    }
    
    public HashSet<String> getSet(String label, String thedefault) {
        String values;
        try {
        	values = getString(label);
        } catch (NoSuchElementException e) {
        	values = thedefault;
        }
        String[] tokens = values.split(",");
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i].trim().toLowerCase());
        }
        return set;
    }
    
    public LinkedList<String> getList(String label, String thedefault) {
    	String values;
        try {
        	values = getString(label);
        } catch (NoSuchElementException e) {
        	values = thedefault;
        }
        if(!values.equals("")) {
            String[] tokens = values.split(",");
            LinkedList<String> set = new LinkedList<String>();
            for (int i = 0; i < tokens.length; i++) {
                set.add(tokens[i].trim().toLowerCase());
            }
            return set;
        }else {
        	return new LinkedList<String>();
        }
    }
    
    public String getString(String label) throws NoSuchElementException {
        String value = properties.getProperty(label);
        if (value == null) {
        	upToDate = false;
            throw new NoSuchElementException("Config did not contain: " + label);
        }
        return value;
    }
    
    public String getString(String label, String thedefault) {
    	String value;
    	try {
        	value = getString(label);
        } catch (NoSuchElementException e) {
        	value = thedefault;
        }
        return value;
    }
    
    public String linkedListToString(LinkedList<String> list) {
    	if(list.size() > 0) {
    		String compounded = "";
    		boolean first = true;
        	for (String value : list) {
        		if (first) {
        			compounded = value;
        			first = false;
        		} else {
        			compounded = compounded + "," + value;
        		}
        	}
        	return compounded;
    	}
    	return "";
    }
    
    
    // Config creation method
    public void createConfig() {
    	try{
    		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(plugin.pluginConfigLocation)));
    		out.write("#\r\n");
    		out.write("# CommandPoints Configuration\r\n");
    		out.write("#\r\n");
    		out.write("\r\n");
    		out.write("# Point Gain/Loss Console Output\r\n");
    		out.write("#	Use this option if you wish to have events\r\n");
    		out.write("#	regarding the gain or loss of command points\r\n");
    		out.write("#	show up in your console.\r\n");
    		out.write("gainlossConsoleOutput=" + gainlossConsoleOutput + "\r\n");
    		out.write("\r\n");
    		out.write("# Log Events\r\n");
    		out.write("#	Here you can list the events that you wish\r\n");
    		out.write("#	to appear in your CommandPoints log (separated\r\n");
    		out.write("#	by spaces). Example: gain loss check\r\n");
    		out.write("#\r\n");
    		out.write("#	Possible events:\r\n");
    		out.write("#		gain - Whenever a player gains points\r\n");
    		out.write("#		loss - Whenever a player loses/spends points\r\n");
    		out.write("#		check - Whenever a player checks his points\r\n");
    		out.write("#		newaccount - Whenever a new account is made\r\n");
    		out.write("logEvents=" + logEvents + "\r\n");
    		out.write("\r\n");
    		out.write("# Reduce overhead\r\n");
    		out.write("#	With this set as true, the points database will\r\n");
    		out.write("#	only be saved on a clean server stop. This is not\r\n");
    		out.write("#	recommended and should only be used by those\r\n");
    		out.write("#	who understand its consequences.\r\n");
    		out.write("reduceOverhead=" + reduceOverhead + "\r\n");
    		out.write("\r\n");
    		out.write("# Receive Notify\r\n");
    		out.write("#	With this set to true, players will be notified\r\n");
    		out.write("#	when you manually grant them commandpoints.\r\n");
    		out.write("receiveNotify=" + receiveNotify + "\r\n");
    		out.close();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
}
