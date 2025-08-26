package javabaseproject.javabase.framework.commandline.controllers;

import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.generators.FactoryGenerator;
import javabaseproject.javabase.framework.generators.ModelGenerator;

import java.io.IOException;

/**
 * control and handle the factory commands
 *
 * @author AsemNajee
 * @version 1.0
 */
public class FactoryController {
    public static void make(String modelName, boolean isNew) throws IOException {
        String content = new FactoryGenerator(modelName, false).factoryFile();
        FileHandler.of(FilePaths.getFactoryPath(modelName)).write(content);
        /*
          add new constructor to the model to be able to create new item with all fields data
         */
        if(!isNew) {
            String modelContent = FileHandler.of(FilePaths.getModelPath(modelName)).read();
            FileHandler.of(FilePaths.getModelPath(modelName))
                    .write(modelContent.replaceAll(
                            "(public( )+class .* extends( )+Model( )+<.*>( )+\\{)",
                            "$1\n\n\t" + ModelGenerator.constructorForAllFields(modelName))
                    );
        }
        Command.printf("g[Factory %sFactory Created Successfully.]", modelName);
    }

    public static void drop(String modelName){
        if(FileHandler.of(FilePaths.getFactoryPath(modelName)).delete()){
            Command.printf("y{Factory %s has been deleted}", modelName + "Factory");
        }else{
            Command.printf("r{Factory %s Not deleted}", modelName + "Factory");
        }
    }
}
