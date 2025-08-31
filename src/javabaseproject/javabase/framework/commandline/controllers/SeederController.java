package javabaseproject.javabase.framework.commandline.controllers;

import javabaseproject.javabase.App;
import javabaseproject.javabase.Register;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.generators.SeederGenerator;

import java.io.IOException;

public class SeederController {

    public static void make(String modelName) throws IOException {
        if(!FileHandler.of(FilePaths.getFactoryPath(modelName)).exists()){
            Command.printTemplate("the model has not factory, \nplease create factory first", "r");
        }
        FileHandler.of(FilePaths.getSeederPath(modelName))
                .write(new SeederGenerator(modelName).seederFile());
        Command.printTemplate("Seeder " + modelName + " Has created successfully.}");
    }

    public static void drop(String modelName){
        if(FileHandler.of(FilePaths.getSeederPath(modelName)).delete()){
            Command.printTemplate("Seeder " + modelName + " has deleted.}", "y");
        }else{
            Command.printTemplate("Seeder " + modelName + " not deleted.}", "r");
        }
    }

    public static void seed(String modelName) throws Exception {
        Recorder.getRecordedClass(modelName).getSeeder().run();
        Command.printTemplate("Data for " + modelName + " has been inserted to database}");
    }

    public static void seed() throws Exception {
        for(var model : Register.getModels().values()){
            if(model.getSeeder() != null)
                seed(model.getName());
        }
        Command.printTemplate("All models seeded to the database successfully.");
    }
}
