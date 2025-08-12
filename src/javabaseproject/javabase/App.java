package javabaseproject.javabase;

import java.util.Arrays;

import javabaseproject.Main;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.framework.exceptions.ExceptionHandler;

/**
 * 
 * @author AsemNajee
 */
public class App {
    public static void start() throws Exception {
        start(Main::main);
    }

    public static void start(CheckedRunnable fn) throws Exception {
        ExceptionHandler.handle(() -> {
            MyModels.getRegisteredModels();
            Connector.start();
            fn.run();
            Connector.close();
        });
    }
}
