package javabaseproject.javabase.framework.commandline.controllers;

import javabaseproject.ENV;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.generators.PivotGenerator;

import java.io.File;
import java.io.IOException;

public class PivotController {
    public static void make(String pivotName, String firstModel, String secondModel) throws IOException {
        String pivotFile = new PivotGenerator(pivotName, firstModel, secondModel, toInstanceName(firstModel), toInstanceName(secondModel)).PivotFile();
        if(createPivotFile(pivotName, pivotFile) == null){
            Command.println("r{Pivot not created}");
        }else{
            Command.println("g[Pivot created successfully]");
        }
    }

    private static String createPivotFile(String pivotName, String pivotContent) throws IOException {
        File file = new File(FilePaths.getPivotPath(pivotName));
        if (file.isFile()) {
            Command.println("r{Pivot is already exists.}");
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
}
