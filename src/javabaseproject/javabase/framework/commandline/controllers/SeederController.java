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
            Command.println("""
                    +--------------------------------+
                    |   the model has not factory,   |
                    |   please create factory first  |
                    |                                |
                    |      g{make:factory {model}}      |
                    +--------------------------------+
                    """.replace("{model}", modelName + " ".repeat(Math.max(7 - modelName.length(), 0)))
            );
        }
        FileHandler.of(FilePaths.getSeederPath(modelName))
                .write(new SeederGenerator(modelName).seederFile());
        Command.printf("g{Seeder %s Has created successfully.}", modelName);
    }

    public static void drop(String modelName){
        if(FileHandler.of(FilePaths.getSeederPath(modelName)).delete()){
            Command.printf("y{Seeder %s has deleted.}", modelName);
        }else{
            Command.printf("r{Seeder %s not deleted.}", modelName);
        }
    }

    public static void seed(String modelName) throws Exception {
        Recorder.getRecordedClass(modelName).getSeeder().run();
        Command.printf("g{Data for %s has been inserted to database}", modelName);
    }

    public static void seed() throws Exception {
        for(var model : Register.getModels().values()){
            if(model.getSeeder() != null)
                seed(model.getName());
        }
        Command.println("g[All models seeded to the database successfully.]");
    }
}
