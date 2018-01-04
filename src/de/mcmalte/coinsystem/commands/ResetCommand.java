package de.mcmalte.coinsystem.commands;

import de.mcmalte.coinsystem.api.CoinsAPI;
import de.mcmalte.coinsystem.Data;
import de.mcmalte.coinsystem.Main;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.minetur.mineturapi.api.MineturCommand;
import de.minetur.mineturapi.api.MineturPlayer;
import de.minetur.mineturapi.api.MineturServer;

public class ResetCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("removecoins")) {
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
					if (CoinsAPI.playerExists(uuid)) {
						CoinsAPI.resetCoins(uuid);
						mp.sendMessage(Data.Prefix + "§2 " + args[0] + "'s §aCoins wurden resettet.");
					} else {
						mp.sendMessage(Data.Prefix + "§cDieser Spieler war noch nie auf  dem Netzwerk.");
					}
				} else
					mp.sendMessage("§cSyntax: /resetcoins <Spieler>");
			} else {
				mp.sendMessage(Data.noPerm);
			}
		}
		return false;
	}
}
