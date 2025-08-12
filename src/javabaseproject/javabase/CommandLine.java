/**
 *   >> Al-Reecha .~
 *   << BY : Asem Najee >>
 */
package javabaseproject.javabase;

import javabaseproject.javabase.framework.commandline.DatabaseCommands;
import javabaseproject.javabase.framework.commandline.FactoryCommands;
import javabaseproject.javabase.framework.commandline.InputCommand;
import javabaseproject.javabase.framework.commandline.ModelCommands;

import java.util.Scanner;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class CommandLine {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(
                           """
                           - run                    : run the project
                           - make:model <ModelName> : create new model and register it
                                [-f]                    :> create factory and seeder for model
                                [-key=<attribute>]      :> set and attribute as a primary key for model
                           - drop:model <ModelName> : drop the model and delete it from registered models
                                [-m]                    :> delete model java class file from models package
                           - db:drop                : drop all database
                           - db:migrate             : migrate models to the database for first time only
                                <ModelName>             :> migrate a specific model
                           - db:init                : create the database
                           - db:seed                : seed data to database
                               <ModelName>             :> seed a specific model
                           - 0                      : exit
                           """);
            System.out.print("JavaBase:> ");
            InputCommand inpc = new InputCommand(in.nextLine());
            if(inpc.equals("0")){
                break;
            }
            if(inpc.equals("run")){
                App.start();
            }
            if(inpc.isCommandFor(ModelCommands.class)){
                ModelCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"), inpc.getMatcher());
            }
            if(inpc.isCommandFor(DatabaseCommands.class)){
                DatabaseCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"));
            }
            if(inpc.isCommandFor(FactoryCommands.class)){
                FactoryCommands.handle(inpc.getMatcher().group("verb"), inpc.getMatcher().group("model"));
            }
        }
        System.out.println("Program Finished");
    }
}