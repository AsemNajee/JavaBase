package javabaseproject;

import javabaseproject.javabase.App;
import javabaseproject.javabase.CommandLine;

/**
 * the entry point of the command line helper library .
 */
public class DevHandler {
    public static void main(String[] args) throws Exception {
        App.start(() -> CommandLine.main(args));
    }
}
