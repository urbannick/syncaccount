package net.xornick.syncaccount.sql;

import net.xornick.syncaccount.SyncAccount;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    String user;
    String database;
    String password;
    String port;
    String hostname;
    Connection connection;
    public boolean shutdown;
    static SyncAccount instance;

    public MySQL(FileConfiguration config) {
        user = "";
        database = "";
        password = "";
        port = "";
        hostname = "";
        connection = null;
        shutdown = false;
        hostname = config.getString("database.host");
        port = config.getString("database.port");
        database = config.getString("database.database");
        user = config.getString("database.username");
        password = config.getString("database.password");
    }

    public Connection open() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, user, password);
        }
        catch (SQLException e) {
            MySQL.instance.getServer().getLogger().severe("Could not connect to MySQL server! Plugin shutting down. Error: " + e.getMessage());
            shutdown = true;
        }
        catch (ClassNotFoundException e2) {
            MySQL.instance.getServer().getLogger().severe("JDBC Driver not found!");
            shutdown = true;
        }
        return connection;
    }

    public boolean checkConnection() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection(final Connection c) {
        try {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        catch (SQLException ex) {}
    }

    static {
        MySQL.instance = SyncAccount.getInstance();
    }
}
