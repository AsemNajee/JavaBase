
import java.io.IOException;
import javabaseproject.javabase.App;
import javabaseproject.javabase.core.commandline.ModelCommands;

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
     * - 
     * 
     */
    public static void handle(String []args) throws IOException, Exception{
        
        switch(args[0]){
            case "make:model" : {
                ModelCommands.handle(args);
            }break;
            case "run" : {
                App.start();
            }
        }
    }
}