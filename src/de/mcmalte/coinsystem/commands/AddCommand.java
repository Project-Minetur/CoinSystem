package de.mcmalte.coinsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.mcmalte.coinsystem.Data;
import de.mcmalte.coinsystem.api.CoinsAPI;
import de.minetur.mineturapi.api.MineturCommand;
import de.minetur.mineturapi.api.MineturPlayer;
import de.minetur.mineturapi.api.MineturServer;

public class AddCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("addcoins")) {
			if (MineturCommand.isConsole(sender)) {
				sender.sendMessage(Data.mustBePlayer);
				return true;
			}
			MineturPlayer mp = MineturServer.getPlayer(sender.getName());
			if (args.length == 2) {
				if (mp.hasPermission("minetur.coins.admin")) {
					if (MineturServer.getPlayer(args[0]).getUUID() == null) {
						mp.sendMessage(Data.Prefix + "§cDieser Spieler existiert nicht in der Mojang-Datenbank.");
						return true;
					}
					String uuid = MineturServer.getPlayer(args[0]).getUUID().toString();
					if (!containsNumbers(args[1])) {
						mp.sendMessage(Data.Prefix + "§cDu kannst nur Zahlen verwenden.");
						return true;
					}
					try {
						long coins = Long.parseLong(args[1]);
						if ((coins + mp.getCoins()) > Long.MAX_VALUE) {
							mp.sendMessage(
									Data.Prefix + "§cEin Spieler darf maximal " + Long.MAX_VALUE + " Coins haben.");
						}
						if (CoinsAPI.playerExists(uuid)) {
							CoinsAPI.addCoins(uuid, Long.valueOf(coins));
							if (Data.BroadcastAddCoins) {
								Bukkit.broadcastMessage(Data.Prefix + "§7Dem Spieler §2" + args[0] + " §awurden §2"
										+ coins + " §aCoins hinzugefügt.");
								Bukkit.broadcastMessage(Data.Prefix
										+ "§aDu willst auch? §2/buy §aoder nimm an §2Events §8/ §2Gewinnspielen §ateil.");
							} else {
								MineturServer.getPlayer(args[0]).sendMessage(
										Data.Prefix + "§aDir §awurden §2" + coins + " §aCoins hinzugefügt.");
							}
						} else {
							CoinsAPI.createPlayer(uuid);
							MineturServer.getConsole().sendMessage(
									"[CoinSystem] Die UUID " + uuid + " wird nun in der Datenbank registriert.");
							mp.sendMessage(Data.Prefix
									+ "§cDieser Spieler wurde soeben in der Datenbank registriert. Bitte versuche es erneut.");
						}
					} catch (Exception ex) {
						mp.sendMessage("§cEin Spieler darf maximal " + Long.MAX_VALUE + " Coins haben.");
					}
				} else {
					mp.sendMessage("§cSyntax: /addcoins <Spieler> <Coins>");
					return true;
				}
			} else {
				mp.sendMessage(Data.noPerm);
			}
		}
		return false;
	}

	public static boolean containsNumbers(String str) {
		try {
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
}
