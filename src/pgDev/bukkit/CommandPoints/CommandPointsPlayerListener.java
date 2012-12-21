package pgDev.bukkit.CommandPoints;

import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Handle events for all Player related events
 * 
 * @author PG Dev Team (Devil Boy)
 */
public class CommandPointsPlayerListener implements Listener {
    private final CommandPoints plugin;

    public CommandPointsPlayerListener(CommandPoints instance) {
        plugin = instance;
    }

    // Event functions below
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	if (!plugin.hasAccount(event.getPlayer().getName())) {
    		plugin.makeAccount(event.getPlayer().getName(), plugin, plugin.pluginSettings.initialcp);
    	}
    }
}

