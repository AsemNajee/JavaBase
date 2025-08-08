import java.io.IOException;

/**
 * to change this file to byte code
 *
 * javac .\javabase\core\commandline\ModelCommands.java
 * javac java\io\IOException.java
 */

/**
 * command line helper to fast develop by the library    
 * @author Asem Abdullah Najee
 */
public class JavaBase {
    public static void print(String text){
        System.out.println(text);
    }
    public static void main(String[] args) throws IOException, Exception {
        if(args == null){
            print("help list");
        }
        handle(args);
    }

    /**
     * Commands are: 
     * 
     * - make:model <ModelName> -> create new model
     * - run -> start the application
     * - migrate -> migrate models to database
     * - init -> create database
     * - 
     * 
     */
    public static void handle(String []args) throws IOException, Exception{
        switch(args[0]){
            case "make:model" -> {
                javabaseproject.javabase.core.commandline.ModelCommands.handle(args);
            }
            case "run" -> {
                javabaseproject.javabase.App.start();
            }
            case "migrate" -> {
                javabaseproject.javabase.core.database.Migration.migrateAll();
            }
            case "init" -> {
                javabaseproject.javabase.core.database.Migration.initDatabase();
            }
        }
    }
}