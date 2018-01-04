package de.mcmalte.coinsystem.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

import de.mcmalte.coinsystem.database.MySQL;
import de.minetur.mineturapi.api.MineturServer;

public class CoinsAPI {

	public static boolean playerExists(String uuid) {
		try {
			ResultSet rs = MySQL.query("SELECT * FROM CoinSystem WHERE UUID='" + uuid + "'");
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void createPlayer(String uuid) {
		if (!playerExists(uuid)) {
			MySQL.update("INSERT INTO CoinSystem (UUID, COINS) VALUES ('" + uuid + "', '0');");
		}
	}

	public static Long getCoins(String uuid) {
		long i = Long.valueOf(0L).longValue();

		if (playerExists(uuid)) {
			try {
				ResultSet rs = MySQL.query("SELECT * FROM CoinSystem WHERE UUID='" + uuid + "'");
				if (rs.next()) {
					Long.valueOf(rs.getLong("COINS"));
				}
				i = Long.valueOf(rs.getLong("COINS")).longValue();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return Long.valueOf(i);
	}

	public static void setCoins(String uuid, Long coins) {
		if (playerExists(uuid)) {
			String name = MineturServer.getPlayer(UUID.fromString(uuid)).getName();
			MySQL.update("UPDATE CoinSystem SET COINS='" + coins + "' WHERE UUID='" + uuid + "'");
			CoinChangeEvent event = new CoinChangeEvent(Bukkit.getServer().getPlayer(name));
			Bukkit.getServer().getPluginManager().callEvent(event);
		}
	}

	public static void addCoins(String uuid, Long coins) {
		if (playerExists(uuid)) {
			setCoins(uuid, Long.valueOf(getCoins(uuid).longValue() + coins.longValue()));
		}
	}

	public static void removeCoins(String uuid, Long coins) {
		if (playerExists(uuid)) {
			setCoins(uuid, Long.valueOf(Long.valueOf(getCoins(uuid).longValue()).longValue() - coins.longValue()));
		}
	}

	public static void resetCoins(String uuid) {
		if (playerExists(uuid)) {
			setCoins(uuid, Long.valueOf(getCoins(uuid).longValue() - Long.valueOf(getCoins(uuid).longValue())));
		}
	}
}
