package javabaseproject.javabase;

import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.exceptions.ExceptionHandler;

/**
 * this is the container of the application .
 * Is register all
 * @author AsemNajee
 */
public class App {
    public static void start(CheckedRunnable fn) throws Exception {
        ExceptionHandler.handle(() -> {
            Register.getRegisteredModels();
            Connector.start();
            try{
                fn.run();
            }finally {
                Connector.close();
            }
        });
    }
}
