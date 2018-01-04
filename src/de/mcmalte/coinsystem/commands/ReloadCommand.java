package de.mcmalte.coinsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.mcmalte.coinsystem.Data;
import de.mcmalte.coinsystem.commands.config.ConfigUtil;
import de.minetur.mineturapi.api.MineturCommand;
import de.minetur.mineturapi.api.MineturPlayer;
import de.minetur.mineturapi.api.MineturServer;

public class ReloadCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("reloadcoins")) {
			if (MineturCommand.isConsole(sender)) {
				sender.sendMessage(Data.mustBePlayer);
				return true;
			}
			MineturPlayer mp = MineturServer.getPlayer(sender);
			if(Data.debug){
				if (mp.hasPermission("minetur.coins.admin")) {
					if (args.length == 0) {
						ConfigUtil.reloadConfig();
						mp.sendMessage(Data.Prefix
								+ "§cPlease note that this command just reloads the config and not reconnects your MySQL connection.");
						mp.sendMessage(Data.Prefix + "§aReload complete.");
						return false;
					} else {
						mp.sendMessage("§cSyntax: /reloadcoins");
						return true;
					}
				} else {
					mp.sendMessage(Data.noPerm);
					return true;
				}
			}else{
				if (mp.hasPermission("minetur.coins.admin")) {
						mp.sendMessage(Data.Prefix + "§cYou have to activate the debug-mode to use this feature.");
						return true;
				} else {
					mp.sendMessage(Data.noPerm);
					return true;
				}
			}
		}
		return false;
	}
}
