package javabaseproject.javabase.core.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javabaseproject.javabase.config.ENV;
import static javabaseproject.javabase.core.database.Drivers.MYSQL;

/**
 * 
 * @author AsemNajee
 */
public class Connector {
    private static java.sql.Connection c;
    private static java.sql.Connection cWithoutDB;

    public static java.sql.Connection getConnection() throws SQLException {
        return getConnection(getUri(), c);
    }
    public static java.sql.Connection getConnectionWithoutDatabaseName() throws SQLException {
        System.out.println(getUri(false));
        return getConnection(getUri(false), cWithoutDB);
    }
    private static java.sql.Connection getConnection(String uri, java.sql.Connection conn) throws SQLException {
            if(conn != null)
                return conn;
            
            conn = switch(ENV.DRIVER){
                case MYSQL -> DriverManager.getConnection(uri, "root", "");
                default -> DriverManager.getConnection(uri, "root", "");
            };
            return conn;
    }
    public static void start() throws SQLException {
        getConnectionWithoutDatabaseName();
        getConnection();
    }

    private static String getUri(){
        return getUri(true);
    }
    private static String getUri(boolean withDBName){
        return switch(ENV.DRIVER){
            case MYSQL -> "jdbc:mysql://localhost:3306" + (withDBName ? "/" + ENV.DATABASE_NAME : "");
            default -> "jdbc:mysql://localhost:3306" + (withDBName ? "/" + ENV.DATABASE_NAME : "");
        };
    }

    public static void close() throws SQLException {
        if(c != null) {
            c.close();
        }
        if(cWithoutDB != null) {
            cWithoutDB.close();
        }
    }
}
