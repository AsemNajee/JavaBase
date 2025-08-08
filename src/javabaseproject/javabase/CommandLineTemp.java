/**
 *   >> Al-Reecha .~
 *   << BY : Asem Najee >>
 */
package javabaseproject.javabase;

import javabaseproject.javabase.core.commandline.ModelCommands;
import javabaseproject.javabase.core.database.Migration;
import javabaseproject.javabase.core.database.Seeder;

import java.io.IOException;
import java.util.Scanner;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class CommandLineTemp {

    public static void main(String[] args) throws IOException, Exception {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("""
                           - make:model <ModelName> : to create new model and register it
                           - migrate                : migrate models to the database for first time only
                           - run                    : run the project
                           - init                   : create the database
                           - seed                   : seed data to database
                           - 0                      : exit
                           """);
            System.out.print("JavaBase > ");
            String[] input = in.nextLine().split(" ");
            switch (input[0]) {
                case "make:model" -> {
                    ModelCommands.handle(input);
                }
                case "run" -> {
                    App.start();
                }
                case "migrate" -> {
                    Migration.migrateAll();
                }
                case "init" -> {
                    Migration.initDatabase();
                }
                case "seed" -> {
                    App.start(rgs -> {
                        try {
                            Seeder.seed();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        return null;
                    });
                }
                case "0" -> {
                    break;
                }
            }
        }
    }
}
