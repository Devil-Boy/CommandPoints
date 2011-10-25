package pgDev.bukkit.CommandPoints;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle events for all Player related events
 * @author DevilBoy
 */
public class CommandPointsPlayerListener extends PlayerListener {
    private final CommandPoints plugin;

    public CommandPointsPlayerListener(CommandPoints instance) {
        plugin = instance;
    }

    // Event functions below
    
    public void onPlayerJoin(PlayerJoinEvent event) {
    	if (!plugin.hasAccount(event.getPlayer().getName())) {
    		plugin.makeAccount(event.getPlayer().getName());
    	}
    }
}

