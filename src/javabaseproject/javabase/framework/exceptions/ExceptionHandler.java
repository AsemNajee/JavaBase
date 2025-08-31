package javabaseproject.javabase.framework.exceptions;

import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.framework.commandline.Command;

public class ExceptionHandler {
    public static void handle(CheckedRunnable fn) {
        try{
            fn.run();
        }catch (Exception e){
            print(e);
            e.printStackTrace();
        }
    }

    public static void print(Exception e){
        Command.printTemplate(e.getMessage(), "r");
    }
}
