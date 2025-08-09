package javabaseproject.javabase;

import java.sql.SQLException;
import java.util.function.Function;

import javabaseproject.Main;
import javabaseproject.javabase.core.database.Connector;

/**
 * 
 * @author AsemNajee
 */
public class App {
    public static void start() throws Exception {
        start(Main::main);
    }

    public static void start(Startable fn) throws Exception {
        MyModels.registerAll();
        Connector.start();
        fn.start();
        Connector.close();
    }
}
interface Startable{
    void start() throws Exception;
}
