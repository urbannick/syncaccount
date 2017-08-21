package net.xornick.syncaccount;

import net.xornick.syncaccount.commands.RegisterCommand;
import net.xornick.syncaccount.commands.SyncAccountCommand;
import net.xornick.syncaccount.sql.Database;
import net.xornick.syncaccount.sql.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public final class SyncAccount extends JavaPlugin {

    private static SyncAccount instance;
    private MySQL sql;
    private Connection connection;
    private Database database;
    private String API_KEY;
    private String API_URL;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerCommands();
        API_KEY = getConfig().getString("api.key");
        API_URL = getConfig().getString("api.url");


    }

    private void registerCommands() {
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("syncaccount").setExecutor(new SyncAccountCommand());
    }

    public static SyncAccount getInstance() {
        return instance;
    }

    public MySQL getSql() {
        return sql;
    }

    public Connection getConnection() {
        return connection;
    }

    public Database getDatabase() {
        return database;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getAPI_URL() {
        return API_URL;
    }
}
