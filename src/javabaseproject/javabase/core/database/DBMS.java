package javabaseproject.javabase.core.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * execute commands and queries on dbms without specific database name
 */
public class DBMS {
    private static final Connection conn;

    static {
        try {
            conn = Connector.getConnectionWithoutDatabaseName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean execute(String sql) throws SQLException {
        return conn.createStatement().execute(sql);
    }
}