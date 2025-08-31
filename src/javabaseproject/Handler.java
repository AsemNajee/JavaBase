package javabaseproject;

import javabaseproject.javabase.App;

/**
 * Entry point for the application please start from here and don't run any other files
 * just <b>call your main method from the main method of this class</b>
 * don't forget to call your main method inside App.start()
 */
public class Handler {
    public static void main(String[] args) throws Exception {
        App.start(() -> Test.main(args));
    }
}
