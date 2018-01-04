package de.mcmalte.coinsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.mcmalte.coinsystem.Data;
import de.minetur.mineturapi.api.MineturCommand;
import de.minetur.mineturapi.api.MineturPlayer;
import de.minetur.mineturapi.api.MineturServer;

public class HelpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("helpcoins")) {
		if(MineturCommand.isConsole(sender)){
			sender.sendMessage(Data.mustBePlayer);
			Bukkit.getServer().dispatchCommand(sender, "help CoinSystem");
			return true;
		}
		MineturPlayer mp = MineturServer.getPlayer(sender);
				if (mp.hasPermission("minetur.coins.admin")) {
					mp.sendMessage("");
					mp.sendMessage("§7§m---------§r§7>> §2§lCoins-Hilfe (ADMIN) §7<<§m---------");
					mp.sendMessage("§2/coins [Spieler] §8- §aZeigt deine Coins oder die eines Spielers");
					mp.sendMessage("§2/addcoins <Spieler> <Coins> §8- §aF§gt einem Spieler Coins hinzu");
					mp.sendMessage("§2/removecoins <Spieler> <Coins> §8- §aEntfernt Coins von einem Spieler");
					mp.sendMessage("§2/setcoins <Spieler> <Coins> §8- §aSetzt die Coins eines Spielers");
					mp.sendMessage("§7§m---------------§r§7>> <<§m---------------");
					mp.sendMessage("");
					return true;
				} else if (mp.hasPermission("minetur.coins.vip")) {
					mp.sendMessage("");
					mp.sendMessage("§7§m---------§r§7>> §2§lCoins-Hilfe (VIP) §7<<§m---------");
					mp.sendMessage("§2/coins [Spieler] §8- §aZeigt deine Coins oder die eines Spielers");
					mp.sendMessage("§7§m---------------§r§7>> <<§m---------------");
					mp.sendMessage("");
					return true;
				} else if (mp.hasPermission("minetur.coins.user")) {
					mp.sendMessage("");
					mp.sendMessage("§7§m---------§r§7>> §2§lCoins-Hilfe (SPIELER) §7<<§m---------");
					mp.sendMessage("§2/coins §8- §aZeigt deine Coins");
					mp.sendMessage("§7§m---------------§r§7>> <<§m---------------");
					mp.sendMessage("");
					return true;
				} else {
					mp.sendMessage(Data.noPerm);
				}
		}
		return false;
	}
}
