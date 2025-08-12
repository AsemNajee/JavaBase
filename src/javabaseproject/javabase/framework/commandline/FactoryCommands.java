package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.framework.commandline.controllers.FactoryController;

import java.io.IOException;

public class FactoryCommands extends Command{

    /**
     * make:factory Animal -> this will make a factory for model Animal with file name AnimalFactory
     * drop:factory Animal
     */

    public static void handle(String verb, String model) throws IOException {
        switch (verb){
            case "make" -> FactoryController.make(model);
            case "drop" -> FactoryController.drop(model);
        }
    }
}
