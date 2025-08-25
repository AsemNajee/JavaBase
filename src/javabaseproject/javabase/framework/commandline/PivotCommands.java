package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.framework.commandline.controllers.PivotController;

import java.io.IOException;

public class PivotCommands extends Command {
    public static void handle(String commandVerb, String pivotName, String firstModel, String secondModel) throws IOException {
        switch (commandVerb){
            case "make"-> {
                PivotController.make(pivotName, firstModel, secondModel);
            }
        }
    }
}
