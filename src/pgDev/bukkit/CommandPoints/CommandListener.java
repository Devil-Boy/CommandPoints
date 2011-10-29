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
	    			if (plugin.hasPermissions(player, "CommandPoints.help") ){
	    				if (plugin.hasPermissions(player, "CommandPoints.points")) {
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " points - Tells you your amount of points");
		    			}
	    				if( plugin.hasPermissions(player, "CommandPoints.points.other")) {
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " points <username> - Tells you the amount of points another player has");
		    			}
	    				if (plugin.hasPermissions(player, "CommandPoints.give")) {
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " give <username> <quantity> - Gives a user a specified number of points");
		    			}
	    				if (plugin.hasPermissions(player, "CommandPoints.remove")) {
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " remove <username> <quantity> - Removes a specified number of points from a user");
		    			}
	    				if (plugin.hasPermissions(player, "CommandPoints.set")) {
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " set <username> <quantity> - Sets a user to the specified number of points.");
		    			}
	    				if (plugin.hasPermissions(player, "CommandPoints.reset")) {
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " reset - Clears the CommandPoints database");
		    			}
	    				if (plugin.hasPermissions(player, "CommandPoints.give.all")) {
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " <quantity> - Gives all users a certain amount of points.");
		    			}
	    				if (plugin.hasPermissions(player, "CommandPoints.remove.all")){
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " <quantity> - Removes a specified amount of points from all users");
		    			}
	    				if (plugin.hasPermissions(player, "CommandPoints.transfer")) {
		    				player.sendMessage(ChatColor.GREEN + "/" + label + " transfer <username> <quantity> - Transfers a specified number of points from your account to another");
		    			}
	    			} else {
	    				player.sendMessage(ChatColor.RED + "You do not have the permissions to run the help command.");
	    			}
				} else { // Console Help
					sender.sendMessage("===== CommandPoints Console Commands =====");
					sender.sendMessage("/" + label + " points <username> - Tells you the amount of points a player has");
					sender.sendMessage("/" + label + " give <username> <quantity> - Gives a user a specified number of points");
					sender.sendMessage("/" + label + " remove <username> <quantity> - Removes a specified number of points from a user");
					sender.sendMessage("/" + label + " set <username> <quantity> - Sets a user to the specified number of points.");
					sender.sendMessage("/" + label + " reset - Clears the CommandPoints database");
					sender.sendMessage("/" + label + " <quantity> - Gives all users a certain amount of points.");
					sender.sendMessage("/" + label + " <quantity> - Removes a specified amount of points from all users");
				}
			} else if (args[0].equalsIgnoreCase("points")) { // Tell Point Amounts
				if (args.length == 1) { // No playername
					if (sender instanceof Player) { // Own points
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.points")) {
		    				player.sendMessage(ChatColor.GOLD + "You have " + plugin.checkPoints(player.getName(), plugin) + " points.");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to check your own number of points.");
		    			}
					} else {
						sender.sendMessage("You did not specify a player.");
					}
				} else if (args.length > 1) { // Player specified
					if (sender instanceof Player) {
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.points.other")) {
		    				if (plugin.hasAccount(args[1])) {
		    					player.sendMessage(ChatColor.GOLD + args[1] + " has " + plugin.checkPoints(args[1], plugin) + " points.");
		    				} else {
		    					player.sendMessage(ChatColor.RED + "The player you specified does not exist in the database.");
		    				}
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to check your own number of points.");
		    			}
					} else {
						sender.sendMessage(args[1] + " has " + plugin.checkPoints(args[1], plugin) + " points.");
					}
				}
			} else if (args[0].equalsIgnoreCase("give")) { // Give Points
				if (args.length == 1) { // Usage dialog
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.give")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + " <player> <amount> <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give points.");
		    			}
					} else { // Console Output
						sender.sendMessage("Usage: /" + label + " <player> <amount> <reason>");
					}
				} else if (args.length == 2) { // No Quantity or Reason
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.give")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + " <amount> <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give points.");
		    			}
					} else { // Console Output
						sender.sendMessage("Usage: /" + label + args[0] + args[1] + " <amount> <reason>");
					}
				} else if (args.length == 3) { // No Reason
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.give")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + args[2] +" <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give points.");
		    			}
					} else { // Console Output
						sender.sendMessage("Usage: /" + label + args[0] + args[1] + args[2] +" <reason>");
					}
				} else if (args.length > 3) { // Correctly Formatted Query
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.give")) {
		    				try {
		    					plugin.addPoints(args[1], Double.parseDouble(args[2]), remainingWords(args, 3), plugin);
		    					player.sendMessage(ChatColor.GOLD + "You gave " + args[1] + " " + args[2] + " points.");
		    				} catch (NumberFormatException e) {
		    					player.sendMessage(ChatColor.RED + "The amount you specified was invalid.");
		    				}
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give points.");
		    			}
					} else { // Console Output
						try {
	    					plugin.addPoints(args[1], Double.parseDouble(args[2]), remainingWords(args, 3), plugin);
	    					sender.sendMessage("You gave " + args[1] + " " + args[2] + " points.");
	    				} catch (NumberFormatException e) {
	    					sender.sendMessage("The amount you specified was invalid.");
	    				}
					}
				}
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
	
	// List Words After Specified Index
    public String remainingWords(String[] wordArray, int startWord) {
    	String remaining = "";
    	for (int i=startWord; i<wordArray.length; i++) {
    		remaining = remaining.trim() + " " + wordArray[i];
    	}
    	return remaining.trim();
    }

}
