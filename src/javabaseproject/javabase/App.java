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
        start(args -> {
            try {
                Main.main(args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    public static void start(Function<String[], Void> fn) throws Exception {
        MyModels.registerAll();
//        Connector.start();
        fn.apply(null);
//        Connector.getConnection().close();
    }
}
