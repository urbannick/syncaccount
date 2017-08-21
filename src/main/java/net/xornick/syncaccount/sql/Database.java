package net.xornick.syncaccount.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection connection;
    private MySQL sql;

    public Database(Connection connection, MySQL sql) {
        this.connection = null;
        this.sql = null;
        this.connection = connection;
        this.sql = sql;
    }

    public boolean databaseContainsPlayer(String playerName) throws SQLException {
        if (!sql.checkConnection()) {
            connection = sql.open();
        }
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM mc_users WHERE username='" + playerName + "'");
            result = statement.getResultSet();
            if (result.next()) {
                return true;
            }
        }
        finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (result != null && !result.isClosed()) {
                result.close();
            }
        }
        if (statement != null && !statement.isClosed()) {
            statement.close();
        }
        if (result != null && !result.isClosed()) {
            result.close();
        }
        return false;
    }

    public String getForumUsernameFromPlayerName(String playerName) throws SQLException {
        if (sql.checkConnection()) {
            connection = sql.open();
        }
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM mc_users WHERE username='" + playerName + "'");
            result = statement.getResultSet();
            if (result.next()) {
                return result.getString("xf_username");
            }
        }
        finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (result != null && !result.isClosed()) {
                result.close();
            }
        }
        if (statement != null && !statement.isClosed()) {
            statement.close();
        }
        if (result != null && !result.isClosed()) {
            result.close();
        }
        return null;
    }

    public void addUserToSyncDatabase(String xf_user_id, String xf_username, String xf_email, String uuid, String username) throws SQLException {
        if (!sql.checkConnection()) {
            connection = sql.open();
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("INSERT INTO mc_users (`xf_user_id`, `xf_username`, `xf_email`, `uuid`, `username`) VALUES ('" + xf_user_id + "', '" + xf_username + "', '" + xf_email + "', '" + uuid + "', '" + username + "');");
        }
        finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        }
        if (statement != null && !statement.isClosed()) {
            statement.close();
        }
    }
}
