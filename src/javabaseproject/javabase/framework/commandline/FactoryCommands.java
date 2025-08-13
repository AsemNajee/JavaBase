package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.framework.commandline.controllers.FactoryController;

public class FactoryCommands extends Command{

    /**
     * make:factory Animal -> this will make a factory for model Animal with file name AnimalFactory
     * drop:factory Animal
     */

    public static void handle(String verb, String model) throws Exception {
        switch (verb){
            case "make" -> FactoryController.make(model, false);
            case "drop" -> FactoryController.drop(model);
        }
    }
}
