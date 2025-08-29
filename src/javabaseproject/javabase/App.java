package javabaseproject.javabase;

import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.exceptions.ExceptionHandler;

/**
 * this is the container of the application .
 * Is start the application and start connection and register all models
 *
 * @author AsemNajee
 */
public class App {
    public static void start(CheckedRunnable fn) {
        ExceptionHandler.handle(() -> {
            Register.getRegisteredModels();
            try{
                Connector.start();
            } catch (Exception e){
                throw new RuntimeException(e);
            }
            try{
                fn.run();
            }finally {
                Connector.close();
            }
        });
    }
}
