package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.App;
import javabaseproject.javabase.framework.commandline.controllers.SeederController;

public class SeederCommands extends Command{

    /**
     * make:seeder Animal
     * drop:seeder Animal
     * start:seeder
     * start:seeder Animal
     */
    public static void handle(String verb, String model) throws Exception {
        switch (verb){
           case "make" -> SeederController.make(model);
           case "drop" -> SeederController.drop(model);
           case "start" -> {
               if(model == null) {
                    SeederController.seed();
               }else {
                    SeederController.seed(model);
               }
           }
        }
    }
}
