package javabaseproject.javabase.framework.exceptions;

import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;

public class ExceptionHandler {
    public static void handle(CheckedRunnable fn) {
        try{
            fn.run();
        }catch (Exception e){
            e.printStackTrace();
            Command.println("r{" + "-".repeat(e.getMessage().length() + 8) + "}");
            Command.println("r{|\t" + e.getMessage() + "\t|}");
            Command.println("r{" + "-".repeat(e.getMessage().length() + 8) + "}");
        }
    }
}
