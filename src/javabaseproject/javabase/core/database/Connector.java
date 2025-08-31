package javabaseproject.javabase.core.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import javabaseproject.ENV;
import javabaseproject.javabase.config.Drivers;

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
        c = getConnection(getUri(), c);
        return c;
    }

    /**
     * creating if not connect a connection and return it
     * @return connection with the database management without database
     */
    public static java.sql.Connection getConnectionWithoutDatabaseName() throws SQLException {
        cWithoutDB = getConnection(getUri(false), cWithoutDB);
        return cWithoutDB;
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
        return DriverManager.getConnection(uri, "root", "");
    }

    /**
     * start all connection to the database
     */
    public static void start() throws SQLException {
        getConnectionWithoutDatabaseName();
        getConnection();
        if(ENV.DRIVER == Drivers.SQLITE){
            var stmt = c.prepareStatement("PRAGMA FOREIGN_KEYS = ON");
            stmt.execute();
        }
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
            case SQLITE -> "jdbc:sqlite:" + ENV.DATABASE_NAME + ".db";
            case ORACLE -> " ";
        };
    }

    /**
     * close all connections with a database
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
