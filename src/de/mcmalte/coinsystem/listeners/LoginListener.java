package de.mcmalte.coinsystem.listeners;

import java.util.UUID;

import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.mcmalte.coinsystem.Data;
import de.mcmalte.coinsystem.api.CoinsAPI;
import de.minetur.mineturapi.api.MineturPlayer;
import de.minetur.mineturapi.api.MineturServer;

public class LoginListener implements Listener {
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		String uuid = MineturServer.getPlayer(e.getPlayer()).getUUID().toString();
		if (!CoinsAPI.playerExists(uuid)) {
			CoinsAPI.createPlayer(uuid);
			CoinsAPI.addCoins(uuid, Data.CoinsOnFirstJoin);
		}
	}
}
