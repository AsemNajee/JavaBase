package javabaseproject.javabase;

import javabaseproject.Handler;
import javabaseproject.javabase.framework.commandline.*;
import javabaseproject.javabase.framework.commandline.output.Console;
import javabaseproject.javabase.framework.commandline.PivotCommands;

import java.util.Scanner;

/**
 * <h1>JavaBase</h1> is a framework helps you to link your java files with database
 * by one command line {@code make:model <ModelName>} and one command to migrate
 * the data from the file to database is {@code db:migrate <ModelName>} .
 * congratulation now you have a table in your database with name {@code <ModelName>}
 * @author Asem Najee
 */
public class CommandLine {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Command.println(Console.help());
        while (true) {
            Command.print("JavaBase:> ");
            InputCommand inpc = new InputCommand(in.nextLine());
            if(inpc.equals("0")){
                break;
            }else
            if(inpc.equals("-h") || inpc.equals("help")){
                Command.println(Console.help());
            }else
            if(inpc.equals("run")){
                Handler.main(args);
            }else
            if(inpc.isCommandFor(ModelCommands.class)){
                ModelCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"), inpc.getMatcher());
            }else
            if(inpc.isCommandFor(PivotCommands.class)){
                PivotCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("pivot"), inpc.getMatcher().group("first"), inpc.getMatcher().group("second"));
            }else
            if(inpc.isCommandFor(DatabaseCommands.class)){
                DatabaseCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"));
            }else
            if(inpc.isCommandFor(FactoryCommands.class)){
                FactoryCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"));
            }else
            if(inpc.isCommandFor(SeederCommands.class)){
                SeederCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"));
            }else
                Command.println("y{UNKNOWN COMMAND}");
        }
        Command.println("Program Finished");
    }
}