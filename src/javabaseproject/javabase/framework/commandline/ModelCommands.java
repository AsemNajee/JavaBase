package javabaseproject.javabase.framework.commandline;

import java.util.regex.Matcher;

import javabaseproject.javabase.App;
import javabaseproject.javabase.framework.commandline.controllers.ModelController;

public class ModelCommands extends Command{
    private static Matcher command;
    public static void handle(String commandVerb, String modelName, Matcher matcher) throws Exception {
        command = matcher;
        switch (commandVerb){
            case "make" -> {
                App.start(() -> {
                    ModelController.make(
                            modelName,
                            command.group("factory") != null,
                            command.group("key"),
                            command.group("keyType")
                    );
                });
            }
            case "drop" -> {
                App.start(() -> {
                    ModelController.drop(
                            modelName,
                            command.group("force") != null
                    );
                });
            }
            case "register" -> {
                App.start(() -> {
                    RegisterController.register(modelName);
                });
            }
        }
    }
}