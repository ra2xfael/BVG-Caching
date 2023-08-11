package international.raffael.caching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {

    private static boolean useDatabase = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getBoolean("mysql.use");
    private static String host = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.host");
    private static String port = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.port");
    private static String database = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.database");
    private static String user = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.user");
    private static String password = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.password");

    private static Connection con;

    private SQL() {
    }

    public static void connect() {
        if (useDatabase && !isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
                System.out.printf("MySQL connection established %s@%s", user, database);
            } catch (SQLException e) {
                System.out.println("MySQL couldn't connect");
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                MessageUtil.printConsoleMessage("&7MySQL disconnected!");
                System.out.println("MySQL disconnected");
            } catch (SQLException e) {
                MessageUtil.printConsoleMessage("&cMySQL couldn't disconnect!");
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return con != null;
    }

    public static Connection getConnection() {
        return con;
    }

    public static PreparedStatement prepareStatement(String query) throws SQLException {
        if (isConnected()) {
            return con.prepareStatement(query);
        }
        SQL.connect();
        throw new SQLException();
    }
}