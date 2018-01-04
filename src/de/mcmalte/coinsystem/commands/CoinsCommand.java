package de.mcmalte.coinsystem.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.mcmalte.coinsystem.Data;
import de.mcmalte.coinsystem.api.CoinsAPI;
import de.minetur.mineturapi.api.MineturCommand;
import de.minetur.mineturapi.api.MineturPlayer;
import de.minetur.mineturapi.api.MineturServer;

public class CoinsCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("coins")) {
			if (MineturCommand.isConsole(sender)) {
				sender.sendMessage(Data.mustBePlayer);
				return true;
			}
			MineturPlayer mp = MineturServer.getPlayer(sender);
			if (args.length == 0) {
				if (CoinsAPI.playerExists(mp.getUUID().toString())) {
					mp.sendMessage(Data.Prefix + "§aDu hast derzeit §2" + mp.getCoins() + " Coins §a.");
					return true;
				} else {
					CoinsAPI.createPlayer(mp.getUUID().toString());
					mp.sendMessage(Data.Prefix + "§aDu hast derzeit §2" + mp.getCoins() + " Coins §a.");
					return true;
				}
			} else if (args.length == 1) {
				if (mp.hasPermission("minetur.coins.vip") || (mp.hasPermission("minetur.coins.admin"))) {
					UUID target = MineturServer.getPlayer(args[0]).getUUID();
					if (target == null) {
						mp.sendMessage(Data.Prefix + "§cDieser Spieler existiert nicht in der Mojang-Datenbank.");
						return true;
					}
					String uuid = MineturServer.getPlayer(args[0]).getUUID().toString();
					if (CoinsAPI.playerExists(uuid)) {
						mp.sendMessage(Data.Prefix + "§aDer Spieler §2" + args[0] + " §ahat derzeit §2"
								+ MineturServer.getPlayer(args[0]).getCoins() + " Coins§a.");
					} else {
						mp.sendMessage(Data.Prefix + "§cDieser Spieler war noch nie auf dem Netzwerk.");
						return true;
					}
				} else {
					mp.sendMessage(Data.noPerm);
				}
			} else {
				mp.sendMessage(Data.Prefix + "§cSyntax: /coins [Spieler]");
			}
		}
		return false;
	}
}
