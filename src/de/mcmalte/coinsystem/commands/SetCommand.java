package de.mcmalte.coinsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.mcmalte.coinsystem.Data;
import de.mcmalte.coinsystem.api.CoinsAPI;
import de.minetur.mineturapi.api.MineturCommand;
import de.minetur.mineturapi.api.MineturPlayer;
import de.minetur.mineturapi.api.MineturServer;

public class SetCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (MineturCommand.isConsole(sender)) {
			sender.sendMessage(Data.mustBePlayer);
			return true;
		}
		MineturPlayer mp = MineturServer.getPlayer(sender);
		if (mp.hasPermission("minetur.coins.admin")) {
			if (args.length == 2) {
				if (MineturServer.getPlayer(args[0]).getUUID() == null) {
					mp.sendMessage(Data.Prefix + "§cDieser Spieler existiert nicht in der Mojang-Datenbank.");
					return true;
				}
				String uuid = MineturServer.getPlayer(args[0]).getUUID().toString();
				if (!isNumeric(args[1])) {
					sender.sendMessage(Data.Prefix + "§cDu kannst dur Zahlen verwenden.");
					return true;
				}
				if (CoinsAPI.playerExists(uuid)) {
					try {
						long coins = Long.parseLong(args[1]);
						MineturServer.getPlayer(uuid).setCoins(Long.valueOf(coins));
						mp.sendMessage(
								Data.Prefix + "§2 " + args[0] + "s §aCoins wurden auf §2" + coins + " §agesetzt.");
					} catch (Exception ex) {
						mp.sendMessage(Data.Prefix + "§cEin Spieler darf maximal " + Long.MAX_VALUE + " Coins haben.");
					}
				} else {
					mp.sendMessage(Data.Prefix + "§cDieser Spieler war noch nie auf dem Netzwerk");
				}
			} else {
				mp.sendMessage(Data.noPerm);
			}
		} else {
			mp.sendMessage("§cSyntax: /setcoins <Spieler> <Coins>");
			return false;
		}
		return false;
	}

	public static boolean isNumeric(String str) {
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
