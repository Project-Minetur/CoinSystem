package de.mcmalte.coinsystem.commands.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import de.mcmalte.coinsystem.Main;
import de.minetur.mineturapi.api.MineturServer;

public class ConfigUtil {
	
	public static void reloadConfig() {
		File file = new File(Main.getInstance().getDataFolder().getPath(), "config.yml");
		YamlConfiguration.loadConfiguration(file);
		MineturServer.getConsole().sendMessage("[CoinSystem] Config reloaded");
	}
	
	public static void reloadMySQLConfig(){
		File file = new File(Main.getInstance().getDataFolder().getPath(), "mysql.yml");
		YamlConfiguration.loadConfiguration(file);
		MineturServer.getConsole().sendMessage("[CoinSystem] MySQL-config reloaded");
	}

}
