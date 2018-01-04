package de.mcmalte.coinsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.minetur.mineturapi.api.MineturServer;

@SuppressWarnings("unused")
public class MySQL {
	public static String username = "";
	public static String password = "";
	public static String database = "";
	public static String host = "";
	public static String port = "";

	public static void connect() {
		if (!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,
						password);
				MineturServer.getConsole().sendMessage("[CoinSystem] §aMySQL-Verbindung wurde hergestellt.");
			} catch (SQLException ex) {
				ex.getMessage();
				MineturServer.getConsole().sendMessage("[CoinSystem] §cFehler beim herstellen der MySQL-Verbindung.");
				MineturServer.getConsole()
						.sendMessage("[CoinSystem] §cSind die MySQL-Daten in der config eingetragen?");
				MineturServer.getConsole()
						.sendMessage("[CoinSystem] §cSind die eigetragenen MySQL-Daten in der config korrekt?");
				MineturServer.getConsole().sendMessage("[CoinSystem] §cIst die MySQL-Datenbank online?");
				MineturServer.getConsole().sendMessage("[CoinSystem] §cHat der eingetragene User die n§tigen Rechte?");
			}
		}
	}

	public static void disconnect() {
		if (isConnected()) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isConnected() {
		return con != null;
	}

	public static void update(String qry) {
		if (isConnected()) {
			try {
				con.createStatement().executeUpdate(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ResultSet getResult(String qry) {
		if (isConnected()) {
			try {
				return con.createStatement().executeQuery(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String getFirstString(ResultSet rs, int l, String re, int t) {
		try {
			while (rs.next()) {
				if (rs.getString(l).equalsIgnoreCase(re)) {
					return rs.getString(t);
				}
			}
		} catch (Exception localException) {
		}
		return null;
	}

	public static int getFirstInt(ResultSet rs, int l, String re, int t) {
		try {
			while (rs.next()) {
				if (rs.getString(l).equalsIgnoreCase(re)) {
					return rs.getInt(t);
				}
			}
		} catch (Exception localException) {
		}
		return 0;
	}

	public static Connection getConnection() {
		return con;
	}

	public static Connection con;

	public static void closeRessources(ResultSet rs, PreparedStatement st) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException localSQLException) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException localSQLException1) {
			}
		}
	}

	public static void close(PreparedStatement st, ResultSet rs) {
		try {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception localException) {
		}
	}

	public static void executeUpdate(String statement) {
		try {
			PreparedStatement st = con.prepareStatement(statement);
			st.executeUpdate();
			close(st, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void executeUpdate(PreparedStatement statement) {
		try {
			statement.executeUpdate();
			close(statement, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet executeQuery(String statement) {
		try {
			PreparedStatement st = con.prepareStatement(statement);
			return st.executeQuery();
		} catch (Exception localException) {
		}
		return null;
	}

	public static ResultSet executeQuery(PreparedStatement statement) {
		try {
			return statement.executeQuery();
		} catch (Exception localException) {
		}
		return null;
	}

	public static ResultSet query(String query) {
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery(query);
			return stmt.getResultSet();
		} catch (Exception e) {
			System.err.println(e);
		}
		return rs;
	}
}
