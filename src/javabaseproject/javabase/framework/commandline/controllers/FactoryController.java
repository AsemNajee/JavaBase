package javabaseproject.javabase.framework.commandline.controllers;

import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.readycontent.NewContent;

import java.io.IOException;

public class FactoryController {
    public static void make(String modelName) throws IOException {
        String content = NewContent.factoryContent(modelName);
        FileHandler.of(FilePaths.getFactoryPath(modelName)).write(content);

    }

    public static void drop(String modelName){
        FileHandler.of(modelName + "Factory").delete();
    }
}
