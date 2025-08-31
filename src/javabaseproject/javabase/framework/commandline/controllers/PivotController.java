package javabaseproject.javabase.framework.commandline.controllers;

import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.generators.PivotGenerator;

import java.io.File;
import java.io.IOException;

/**
 * control and handle pivot commands
 *
 * @author AsemNajee
 * @version 1.0
 */
public class PivotController {
    public static void make(String pivotName, String firstModel, String secondModel) throws IOException {
        PivotGenerator pivotFile = new PivotGenerator(pivotName, firstModel, secondModel, toInstanceName(firstModel), toInstanceName(secondModel));
        if(createPivotFile(pivotName, pivotFile.PivotFile()) == null){
            Command.printTemplate(
                    "Pivot not created",
                    "r");
        }else{
            Command.printTemplate("Pivot created successfully");
            ModelController.addNewMethodToModel(firstModel, pivotFile.getRelationFromFirstModelToSecondModel());
            ModelController.addNewMethodToModel(secondModel, pivotFile.getRelationFromSecondModelToFirstModel());
            Command.printTemplate("Relations in models are created, see your models");
        }
    }

    private static String createPivotFile(String pivotName, String pivotContent) throws IOException {
        File file = new File(FilePaths.getPivotPath(pivotName));
        if (file.isFile()) {
            Command.printTemplate("Pivot is already exists.", "y");
            return null;
        }
        FileHandler.of(file).write(pivotContent);
        return file.getAbsolutePath();
    }

    public static String toInstanceName(String text){
        return text.replace(text.substring(0, 1), text.substring(0, 1).toLowerCase());
    }
    public static String toClassName(String text){
        return text.replace(text.substring(0, 1), text.substring(0, 1).toUpperCase());
    }
    public static String toPluralName(String name){
        if(name.matches(".*(s|sh|ch|x|z|o)$")){
            return name.concat("es");
        }
        if(name.matches(".*([^aeiou]y)$")){
            return name.replaceAll("y$", "ies");
        }
        return name.concat("s");
    }
}
