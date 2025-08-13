package javabaseproject.javabase.framework.commandline;

import java.util.regex.Matcher;

import javabaseproject.javabase.App;
import javabaseproject.javabase.framework.commandline.controllers.ModelController;
import javabaseproject.javabase.framework.commandline.controllers.RegisterController;

public class ModelCommands extends Command{
    private static Matcher command;
    public static void handle(String commandVerb, String modelName, Matcher matcher) throws Exception {
        command = matcher;
        switch (commandVerb){
            case "make" -> {
                ModelController.make(
                        modelName,
                        command.group("factory") != null,
                        command.group("key"),
                        command.group("keyType")
                );
            }
            case "drop" -> {
                ModelController.drop(
                        modelName,
                        command.group("force") != null
                );
            }
            case "register" -> {
                RegisterController.register(modelName);
            }
        }
    }
}