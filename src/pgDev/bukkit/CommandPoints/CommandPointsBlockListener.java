package pgDev.bukkit.CommandPoints;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * CommandPoints block listener
 * 
 * @author PG Dev Team
 * @author Devil Boy
 */
public class CommandPointsBlockListener extends BlockListener {
    private final CommandPoints plugin;

    public CommandPointsBlockListener(final CommandPoints plugin) {
        this.plugin = plugin;
    }

    //put all Block related code here
}
