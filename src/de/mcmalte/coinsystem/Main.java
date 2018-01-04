package de.mcmalte.coinsystem;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import de.mcmalte.coinsystem.commands.AddCommand;
import de.mcmalte.coinsystem.commands.CoinsCommand;
import de.mcmalte.coinsystem.commands.HelpCommand;
import de.mcmalte.coinsystem.commands.ReloadCommand;
import de.mcmalte.coinsystem.commands.RemoveCommand;
import de.mcmalte.coinsystem.commands.ResetCommand;
import de.mcmalte.coinsystem.commands.SetCommand;
import de.mcmalte.coinsystem.database.MySQL;
import de.mcmalte.coinsystem.listeners.LoginListener;
import de.minetur.mineturapi.api.MineturServer;

public class Main extends JavaPlugin {

	private static Main instance;

	public static Main getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;
		createConfig();
		loadConfig();
		MySQL.connect();
		if (MySQL.isConnected()) {
			getCommand("addcoins").setExecutor(new AddCommand());
			getCommand("removecoins").setExecutor(new RemoveCommand());
			getCommand("setcoins").setExecutor(new SetCommand());
			getCommand("coins").setExecutor(new CoinsCommand());
			getCommand("reloadcoins").setExecutor(new ReloadCommand());
			getCommand("helpcoins").setExecutor(new HelpCommand());
			getCommand("resetcoins").setExecutor(new ResetCommand());
			getServer().getPluginManager().registerEvents(new LoginListener(), this);
			MySQL.update("CREATE TABLE IF NOT EXISTS CoinSystem(UUID VARCHAR(100), COINS long)");
		} else {
			Bukkit.getConsoleSender().sendMessage("[CoinSystem] §cPlugin wird deaktiviert.");
			this.getPluginLoader().disablePlugin(this);
		}
	}

	public void onDisable() {
		MySQL.disconnect();
		instance = null;
	}

	public void createConfig() {
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}
			File file = new File(getDataFolder().getPath(), "mysql.yml");
			File file2 = new File(getDataFolder().getPath(), "config.yml");
			if (!file.exists()) {
				file.createNewFile();
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				config.set("MySQL.hostname", "localhost");
				config.set("MySQL.port", "3306");
				config.set("MySQL.database", "database");
				config.set("MySQL.username", "user");
				config.set("MySQL.password", "password");
				config.save(file);
			}
			if (!file2.exists()) {
				file2.createNewFile();
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file2);
				config.set("CoinsOnFirstJoin", 10);
				config.set("CoinsOnReset", 0);
				config.set("BroadcastAddCoins", false);
				config.set("debug-mode", false);
				config.save(file2);
			}
		} catch (IOException localIOException) {
			MineturServer.getConsole().sendMessage("[CoinSystem] Excpetion: " + localIOException.getMessage());
		}
	}

	public void loadConfig() {
		File file = new File(getDataFolder().getPath(), "mysql.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		MySQL.host = config.getString("MySQL.hostname");
		MySQL.port = config.getString("MySQL.port");
		MySQL.database = config.getString("MySQL.database");
		MySQL.username = config.getString("MySQL.username");
		MySQL.password = config.getString("MySQL.password");
	}

	{
		File file2 = new File(getDataFolder().getPath(), "config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file2);
		Data.CoinsOnFirstJoin = config.getLong("CoinsOnFirstJoin");
		Data.CoinsOnReset = config.getLong("CoinsOnReset");
		Data.BroadcastAddCoins = config.getBoolean("BroadcastAddCoins");
		Data.debug = config.getBoolean("debug-mode");
	}
}
