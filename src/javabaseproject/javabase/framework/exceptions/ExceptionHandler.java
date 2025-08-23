package javabaseproject.javabase.framework.exceptions;

import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;

public class ExceptionHandler {
    public static void handle(CheckedRunnable fn) {
        try{
            fn.run();
        }catch (Throwable e){
            String error = "r{" + "-".repeat(e.getMessage().length() + 8) + "}\n";
            error += "r{|\t" + e.getMessage() + "\t|}\n";
            error += "r{" + "-".repeat(e.getMessage().length() + 8) + "}\n";
            Command.println(error);
            e.printStackTrace();
        }
    }
}
