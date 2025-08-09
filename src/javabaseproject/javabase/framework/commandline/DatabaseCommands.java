package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.Recorder;
import javabaseproject.javabase.core.database.Migration;
import javabaseproject.seeders.Seeder;
import javabaseproject.javabase.output.Colors;

public class DatabaseCommands extends Commands{

    /**
     * @param model is optional and only will make different in db:migrate modelName
     */
    public static void handle(String verb, String model) throws Exception {
        switch (verb){
            case "init" -> {
                Migration.initDatabase();
            }
            case "migrate" -> {
                if(model != null){
                    if(model.matches("[A-Z][A-Za-z0-9]*")){
                        RecordedClass rclass = Recorder.getRecordedClass(model);
                        if(rclass != null){
                            if(Migration.migrate(rclass)){
                                printf("Model %s is migrated successfully", rclass.getName());
                            }else{
                                println("Field to migrate a model", Colors.RED);
                            }
                        }
                    }
                }else{
                    Migration.migrateAll();
                }
            }
            case "seed" -> {
                Seeder.seed();
            }
            case "drop" -> {
                Migration.dropDatabase();
            }
        }
    }
}
