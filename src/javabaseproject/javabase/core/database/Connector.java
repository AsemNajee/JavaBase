package javabaseproject.javabase.core.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import javabaseproject.ENV;

/**
 * 
 * @author AsemNajee
 */
public class Connector {
    /**
     * connection instance with the database
     */
    private static java.sql.Connection c;

    /**
     * connection instance with the db manager but without the db name
     * it's useful when creating the database using "db:init" 
     */
    private static java.sql.Connection cWithoutDB;

    /**
     * creating if not connect a connection and return it
     * @return connection with the database
     */
    public static java.sql.Connection getConnection() throws SQLException {
        return getConnection(getUri(), c);
    }

    /**
     * creating if not connect a connection and return it
     * @return connection with the database management without database
     */
    public static java.sql.Connection getConnectionWithoutDatabaseName() throws SQLException {
        return getConnection(getUri(false), cWithoutDB);
    }

    /**
     * create a connection
     * @param uri the uri to link the database
     * @param conn the instance connection to store the connection reference in it <br/>
     *             this also check if the instance is already linked and return it
     * @return connection
     */
    private static java.sql.Connection getConnection(String uri, java.sql.Connection conn) throws SQLException {
            if(conn != null)
                return conn;
            
            conn = switch(ENV.DRIVER){
                case MYSQL -> DriverManager.getConnection(uri, "root", "");
                default -> DriverManager.getConnection(uri, "root", "");
            };
            return conn;
    }

    /**
     * start all connection to the database
     */
    public static void start() throws SQLException {
        getConnectionWithoutDatabaseName();
        getConnection();
    }

    /**
     * get the uri to link the database depending on the {@link javabaseproject.javabase.config.Drivers}
     * @return the string uri
     */
    private static String getUri(){
        return getUri(true);
    }

    /**
     * get the uri to link the database depending on the {@link javabaseproject.javabase.config.Drivers}
     * @param withDBName state of including the database name in the uri
     * @return the string uri
     */
    private static String getUri(boolean withDBName){
        return switch(ENV.DRIVER){
            case MYSQL -> "jdbc:mysql://localhost:3306" + (withDBName ? "/" + ENV.DATABASE_NAME : "");
            default -> "jdbc:mysql://localhost:3306" + (withDBName ? "/" + ENV.DATABASE_NAME : "");
        };
    }

    /**
     * close all connection with a database
     */
    public static void close() throws SQLException {
        if(c != null) {
            c.close();
        }
        if(cWithoutDB != null) {
            cWithoutDB.close();
        }
    }
}
