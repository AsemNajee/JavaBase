/**
 *   >> Al-Reecha .~
 *   << BY : Asem Najee >>
 */
package javabaseproject.javabase;

import javabaseproject.javabase.framework.commandline.DatabaseCommands;
import javabaseproject.javabase.framework.commandline.InputCommand;
import javabaseproject.javabase.framework.commandline.ModelCommands;

import java.util.Scanner;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class CommandLineTemp {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(
                           """
                           - run                    : run the project
                           - make:model <ModelName> : create new model and register it
                           - drop:model <ModelName> : drop the model and delete it from registered models
                           - db:drop                : drop all database
                           - db:migrate             : migrate models to the database for first time only
                           - db:init                : create the database
                           - db:seed                : seed data to database
                           - 0                      : exit
                           """);
            System.out.print("JavaBase:> ");
            InputCommand inpc = new InputCommand(in.nextLine());
            if(inpc.isCommandFor(ModelCommands.class)){
                ModelCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"), inpc.getMatcher().group("etc"));
            }
            if(inpc.isCommandFor(DatabaseCommands.class)){
                DatabaseCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"));
            }
        }
    }
}