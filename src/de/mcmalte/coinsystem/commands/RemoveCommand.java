package de.mcmalte.coinsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.mcmalte.coinsystem.Data;
import de.mcmalte.coinsystem.api.CoinsAPI;
import de.minetur.mineturapi.api.MineturCommand;
import de.minetur.mineturapi.api.MineturPlayer;
import de.minetur.mineturapi.api.MineturServer;

public class RemoveCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (MineturCommand.isConsole(sender)) {
			sender.sendMessage(Data.mustBePlayer);
			return true;
		}
		MineturPlayer mp = MineturServer.getPlayer(sender);
		if (mp.hasPermission("minetur.coins.admin")) {
			if (args.length == 2) {
				if (MineturServer.getPlayer(args[0]).getUUID() == null) {
					mp.sendMessage(Data.Prefix + "§cDieser Spieler wurde nicht gefunden.");
					return true;
				}
				if (!isNumeric(args[1])) {
					sender.sendMessage(Data.Prefix + "§cDu kannst nur Zahlen verwenden.");
					return true;
				}

				String uuid = MineturServer.getPlayer(args[0]).getUUID().toString();
				long coins = Long.parseLong(args[1]);
				if (CoinsAPI.playerExists(uuid)) {
					if (CoinsAPI.getCoins(uuid).longValue() < coins) {
						mp.sendMessage(Data.Prefix + "§cDieser Spieler hat nicht genügend Coins.");
						return true;
					}
					CoinsAPI.removeCoins(uuid, Long.valueOf(coins));
					mp.sendMessage(Data.Prefix + "§aDem Spieler §2" + args[0] + " §awurden §2" + coins
							+ " §aCoins weggenommen.");
					MineturPlayer target = MineturServer.getPlayer(args[0]);
					if (target.isOnline()) {
						target.sendMessage(Data.Prefix + "§2Dir §awurden §2" + coins + "  §aweggenommen.");
					}

				} else {
					mp.sendMessage(Data.Prefix + "§cDieser Spieler war noch nie auf dem Netzwerk.");
					return true;
				}
			} else {
				mp.sendMessage(Data.Prefix + "§cSyntax: /removecoins <Player> <Coins>");
				return true;
			}
		} else {
			mp.sendMessage(Data.noPerm);
			return true;
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
