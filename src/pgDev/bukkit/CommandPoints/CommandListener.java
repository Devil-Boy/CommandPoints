package pgDev.bukkit.CommandPoints;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
	CommandPoints plugin;
	
	public CommandListener(CommandPoints pluginI) {
		plugin = pluginI;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("commandpoints") || label.equalsIgnoreCase("cp")) {
			if (args.length == 0) { // Help Response
				if (sender instanceof Player) { // Player Help
	    			Player player = (Player)sender;
	    			if(plugin.hasPermissions(player, "CommandPoints.help")){
	    				if(plugin.hasPermissions(player, "CommandPoints.points")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " points - Tells you your amount of points");
		    			}
	    				if(plugin.hasPermissions(player, "CommandPoints.points.other")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " points <username> - Tells you the amount of points another player has");
		    			}
	    				if(plugin.hasPermissions(player, "CommandPoints.give")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " give <username> <quantity> - Gives a user a specified number of points");
		    			}
	    				if(plugin.hasPermissions(player, "CommandPoints.remove")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " remove <username> <quantity> - Removes a specified number of points from a user");
		    			}
	    				if(plugin.hasPermissions(player, "CommandPoints.set")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " set <username> <quantity> - Sets a user to the specified number of points.");
		    			}
	    				if(plugin.hasPermissions(player, "CommandPoints.reset")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " reset - Clears the CommandPoints database");
		    			}
	    				if(plugin.hasPermissions(player, "CommandPoints.give.all")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " <quantity> - Gives all users a certain amount of points.");
		    			}
	    				if(plugin.hasPermissions(player, "CommandPoints.remove.all")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " <quantity> - Removes a specified amount of points from all users");
		    			}
	    				if(plugin.hasPermissions(player, "CommandPoints.transfer")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " transfer <username> <quantity> - Transfers a specified number of points from your account to another");
		    			}
	    			} else {
	    				player.sendMessage(ChatColor.RED + "You do not have the permissions to run the help command.");
	    			}
				} else { // Console Help
					sender.sendMessage(ChatColor.GREEN + "/" + label + " points <username> - Tells you the amount of points a player has");
					sender.sendMessage(ChatColor.GREEN + "/" + label + " give <username> <quantity> - Gives a user a specified number of points");
					sender.sendMessage(ChatColor.GREEN + "/" + label + " remove <username> <quantity> - Removes a specified number of points from a user");
					sender.sendMessage(ChatColor.GREEN + "/" + label + " set <username> <quantity> - Sets a user to the specified number of points.");
					sender.sendMessage(ChatColor.GREEN + "/" + label + " reset - Clears the CommandPoints database");
					sender.sendMessage(ChatColor.GREEN + "/" + label + " <quantity> - Gives all users a certain amount of points.");
					sender.sendMessage(ChatColor.GREEN + "/" + label + " <quantity> - Removes a specified amount of points from all users");
				}
			} else if (args[0].equalsIgnoreCase("points")) { // Tell Point Amounts
				
			} else if (args[0].equalsIgnoreCase("give")) { // Give Points
				
			} else if (args[0].equalsIgnoreCase("remove")) { // Remove Points
				
			} else if (args[0].equalsIgnoreCase("set")) { // Set Points
				
			} else if (args[0].equalsIgnoreCase("reset")) { // Reset Points
				
			} else if (args[0].equalsIgnoreCase("giveall")) { // Give Everyone Points
				
			} else if (args[0].equalsIgnoreCase("removeall")) { // Remove Points from Everyone
				
			} else if (args[0].equalsIgnoreCase("transfer")) { // Transfer Points
				
			}
		}
		return false;
	}

}
