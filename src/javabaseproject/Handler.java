package javabaseproject;

import javabaseproject.javabase.App;
import javabaseproject.javabase.CommandLine;

/**
 * Entry point for the application please start from here and don't run any other files
 * just <b>call your main method from the main method of this class</b>
 */
public class Handler {
    public static void main(String[] args) throws Exception {
//        call your main method inside the lambda
        App.start(() -> {
            CommandLine.main(args);
        });
    }
}
