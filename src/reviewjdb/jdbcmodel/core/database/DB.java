package reviewjdb.jdbcmodel.core.database;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import reviewjdb.model.Model;

public class DB{
    private static final Connection conn;
    
    static {
        conn = Connector.getConnection();
    }
    public static boolean execute(String sql) throws SQLException{
        return conn.createStatement().execute(sql);
    }
    
//    public static ResultSet getRow(Model fromModel, String key, String equalsTo) throws SQLException{
//        Builder.select("").from("");
//        return null;
//    }
}

/**
 * SELECT {COLS} FROM {TABLE}
 * WHERE {COLS} [OPREATOR] {VALUE}
 * GROUB BY {COLS}
 * ORDER BY {COLS}
 * LIMIT [LIMIT]
 */