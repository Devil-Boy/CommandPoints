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
		    				if (plugin.hasAccount(args[1])) { // Check if recipient exists
		    					try {
			    					plugin.addPoints(args[1], Double.parseDouble(args[2]), remainingWords(args, 3), plugin);
			    					player.sendMessage(ChatColor.GOLD + "You gave " + args[1] + " " + args[2] + " points.");
			    				} catch (NumberFormatException e) {
			    					player.sendMessage(ChatColor.RED + "The amount you specified was invalid.");
			    				}
		    				} else {
		    					player.sendMessage(ChatColor.RED + "The player you specified does not have an account.");
		    				}
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give points.");
		    			}
					} else { // Console Output
						if (plugin.hasAccount(args[1])) { // Check if recipient exists
							try {
		    					plugin.addPoints(args[1], Double.parseDouble(args[2]), remainingWords(args, 3), plugin);
		    					sender.sendMessage("You gave " + args[1] + " " + args[2] + " points.");
		    				} catch (NumberFormatException e) {
		    					sender.sendMessage("The amount you specified was invalid.");
		    				}
						} else {
	    					sender.sendMessage("The player you specified does not have an account.");
	    				}
					}
				}
			} else if (args[0].equalsIgnoreCase("remove")) { // Remove Points
				if (args.length == 1) { // Usage dialog
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.remove")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + " <player> <amount> <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to remove points.");
		    			}
					} else { // Console Output
						sender.sendMessage("Usage: /" + label + " <player> <amount> <reason>");
					}
				} else if (args.length == 2) { // No Quantity or Reason
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.remove")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + " <amount> <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to remove points.");
		    			}
					} else { // Console Output
						sender.sendMessage("Usage: /" + label + args[0] + args[1] + " <amount> <reason>");
					}
				} else if (args.length == 3) { // No Reason
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.remove")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + args[2] +" <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to remove points.");
		    			}
					} else { // Console Output
						sender.sendMessage("Usage: /" + label + args[0] + args[1] + args[2] +" <reason>");
					}
				} else if (args.length > 3) { // Correctly Formatted Query
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.remove")) {
		    				if (plugin.hasAccount(args[1])) { // Does the player exist?
		    					try {
			    					plugin.removePoints(args[1], Double.parseDouble(args[2]), remainingWords(args, 3), plugin);
			    					player.sendMessage(ChatColor.GOLD + "You gave removed " + args[2] + " points from " + args[1] + "'s account.");
			    				} catch (NumberFormatException e) {
			    					player.sendMessage(ChatColor.RED + "The amount you specified was invalid.");
			    				}
		    				} else {
		    					player.sendMessage(ChatColor.RED + "The player you specified does not have an account.");
		    				}
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to remove points.");
		    			}
					} else { // Console Output
						if (plugin.hasAccount(args[1])) { // Does the player exist?
							try {
		    					plugin.removePoints(args[1], Double.parseDouble(args[2]), remainingWords(args, 3), plugin);
		    					sender.sendMessage("You gave removed " + args[2] + " points from " + args[1] + "'s account.");
		    				} catch (NumberFormatException e) {
		    					sender.sendMessage("The amount you specified was invalid.");
		    				}
						} else {
	    					sender.sendMessage("The player you specified does not have an account.");
	    				}
					}
				}
			} else if (args[0].equalsIgnoreCase("set")) { // Set Points
				if (args.length == 1) { // Usage dialog
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.set")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + " <player> <amount>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to set points.");
		    			}
					} else { // Console Output
						sender.sendMessage("Usage: /" + label + " <player> <amount>");
					}
				} else if (args.length == 2) { // No amount given
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.set")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + " <amount>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to set points.");
		    			}
					} else { // Console Output
						sender.sendMessage("Usage: /" + label + args[0] + args[1] + " <amount>");
					}
				} else if (args.length > 2) { // Correctly formatted query
					if (sender instanceof Player) { // Player Output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.set")) {
		    				if (plugin.hasAccount(args[1])) { // Does the player exist?
		    					try {
			    					plugin.setPoints(args[1], Double.parseDouble(args[2]), plugin);
			    					player.sendMessage(ChatColor.GOLD + "You have set " + args[1] + "'s account to " + args[2] + " points.");
			    				} catch (NumberFormatException e) {
			    					player.sendMessage(ChatColor.RED + "The amount you specified was invalid.");
			    				}
		    				} else {
		    					player.sendMessage(ChatColor.RED + "The player you specified does not have an account.");
		    				}
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to set points.");
		    			}
					} else { // Console Output
						if (plugin.hasAccount(args[1])) { // Does the player exist?
							try {
								plugin.setPoints(args[1], Double.parseDouble(args[2]), plugin);
		    					sender.sendMessage("You have set " + args[1] + "'s account to " + args[2] + " points.");
		    				} catch (NumberFormatException e) {
		    					sender.sendMessage("The amount you specified was invalid.");
		    				}
						} else {
	    					sender.sendMessage("The player you specified does not have an account.");
	    				}
					}
				}
			} else if (args[0].equalsIgnoreCase("reset")) { // Reset Points
				if (sender instanceof Player) {
	    			Player player = (Player)sender;
	    			if (plugin.hasPermissions(player, "CommandPoints.reset")) {
	    				plugin.clearPoints();
	    				player.sendMessage(ChatColor.GOLD + "Points database reset!");
	    			} else {
	    				player.sendMessage(ChatColor.RED + "You do not have the permission to reset the points database.");
	    			}
				} else {
					plugin.clearPoints();
					sender.sendMessage("Points database reset!");
				}
			} else if (args[0].equalsIgnoreCase("giveall")) { // Give Everyone Points
				if (args.length == 1) { // Usage dialog
					if (sender instanceof Player) {
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.give.all")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + " <amount> <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give everyone points.");
		    			}
					} else {
						sender.sendMessage("Usage: /" + label + args[0] + " <amount> <reason>");
					}
				} else if (args.length == 2) { // Missing reason
					if (sender instanceof Player) {
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.give.all")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + " <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give everyone points.");
		    			}
					} else {
						sender.sendMessage("Usage: /" + label + args[0] + args[1] + " <reason>");
					}
				} else if (args.length > 2) { // Correctly formatted query
					if (sender instanceof Player) { // Player output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.give.all")) {
		    				try {
		    					plugin.addPointsAll(Double.parseDouble(args[1]), remainingWords(args, 2), plugin);
		    					player.sendMessage(ChatColor.GOLD + "You have given everyone " + args[1] + " points.");
		    				} catch (NumberFormatException e) {
		    					player.sendMessage(ChatColor.RED + "The amount you specified was invalid.");
		    				}
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give everyone points.");
		    			}
					} else { // Console output
						try {
	    					plugin.addPointsAll(Double.parseDouble(args[1]), remainingWords(args, 2), plugin);
	    					sender.sendMessage("You have given everyone " + args[1] + " points.");
	    				} catch (NumberFormatException e) {
	    					sender.sendMessage("The amount you specified was invalid.");
	    				}
					}
				}
			} else if (args[0].equalsIgnoreCase("removeall")) { // Remove Points from Everyone
				if (args.length == 1) { // Usage dialog
					if (sender instanceof Player) {
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.remove.all")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + " <amount> <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give everyone points.");
		    			}
					} else {
						sender.sendMessage("Usage: /" + label + args[0] + " <amount> <reason>");
					}
				} else if (args.length == 2) { // Missing reason
					if (sender instanceof Player) {
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.remove.all")) {
		    				player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + " <reason>");
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give everyone points.");
		    			}
					} else {
						sender.sendMessage("Usage: /" + label + args[0] + args[1] + " <reason>");
					}
				} else if (args.length > 2) { // Correctly formatted query
					if (sender instanceof Player) { // Player output
		    			Player player = (Player)sender;
		    			if (plugin.hasPermissions(player, "CommandPoints.remove.all")) {
		    				try {
		    					plugin.removePointsAll(Double.parseDouble(args[1]), remainingWords(args, 2), plugin);
		    					player.sendMessage(ChatColor.GOLD + "You have taken " + args[1] + " points from everyone.");
		    				} catch (NumberFormatException e) {
		    					player.sendMessage(ChatColor.RED + "The amount you specified was invalid.");
		    				}
		    			} else {
		    				player.sendMessage(ChatColor.RED + "You do not have the permission to give everyone points.");
		    			}
					} else { // Console output
						try {
	    					plugin.removePointsAll(Double.parseDouble(args[1]), remainingWords(args, 2), plugin);
	    					sender.sendMessage("You have taken " + args[1] + " points from everyone.");
	    				} catch (NumberFormatException e) {
	    					sender.sendMessage("The amount you specified was invalid.");
	    				}
					}
				}
			} else if (args[0].equalsIgnoreCase("transfer")) { // Transfer Points (0=transfer, 1=otherplayer, 2=amount, 3+=reason)
				if (sender instanceof Player) {
	    			Player player = (Player)sender;
	    			if (plugin.hasPermissions(player, "CommandPoints.remove.all")) { // Can transfer
	    				if (args.length == 1) { // Usage dialog
	    					player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + " <player> <amount> <reason>");
	    				} else if (args.length == 2) { // Missing amount and reason
	    					player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + " <amount> <reason>");
	    				} else if (args.length == 3) { // Missing reason
	    					player.sendMessage(ChatColor.GREEN + "Usage: /" + label + args[0] + args[1] + args[2] + " <reason>");
	    				} else if (args.length > 3) { // Correctly formatted query
	    					if (plugin.hasAccount(args[1])) { // Check if recipient exists
	    						try {
	    							if (plugin.hasPoints(player.getName(), Double.parseDouble(args[2]))) { // Check if giver has enough points
	    								plugin.transferPoints(player.getName(), args[1], Double.parseDouble(args[2]), remainingWords(args, 3), plugin);
				    					player.sendMessage(ChatColor.GOLD + "You have given " + args[2] + " points to " + args[1]);
	    							} else {
	    								player.sendMessage(ChatColor.RED + "You do not have " + args[2] + " points to give.");
	    							}
			    				} catch (NumberFormatException e) {
			    					player.sendMessage(ChatColor.RED + "The amount you specified was invalid.");
			    				}
	    					} else {
	    						player.sendMessage(ChatColor.RED + "The player you specified does not have an account.");
	    					}
	    				}
	    			} else {
	    				player.sendMessage(ChatColor.RED + "You do not have the permission to transfer your points.");
	    			}
				} else {
					sender.sendMessage("This command must be run by a player while in game.");
				}
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
