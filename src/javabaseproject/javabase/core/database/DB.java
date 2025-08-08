package javabaseproject.javabase.core.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DB{
    private static final Connection conn;
    
    static {
        conn = Connector.getConnection();
    }
    public static boolean execute(String sql) throws SQLException{
        return conn.createStatement().execute(sql);
    }
}
