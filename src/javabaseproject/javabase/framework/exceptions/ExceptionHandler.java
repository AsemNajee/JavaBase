package javabaseproject.javabase.framework.exceptions;

import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.output.Colors;

import java.util.function.Function;

public class ExceptionHandler {
    public static void handle(CheckedRunnable fn) {
        try{
            fn.run();
        }catch (Exception e){
            Command.println("-".repeat(e.getMessage().length() + 8), Colors.RED);
            Command.println("|\t" + e.getMessage() + "\t|", Colors.RED);
            Command.println("-".repeat(e.getMessage().length() + 8), Colors.RED);
        }
    }
}
