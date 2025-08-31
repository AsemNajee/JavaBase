package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.Migration;

public class DatabaseCommands extends Command{

    /**
     * @param model is optional and only will make different in db:migrate modelName and db:seed
     */
    public static void handle(String verb, String model) throws Exception {
        switch (verb){
            case "fresh"-> {
                DatabaseCommands.handle("drop", null);
                DatabaseCommands.handle("init", null);
                DatabaseCommands.handle("migrate", null);
            }
            case "init" -> {
                Migration.initDatabase();
                Command.printTemplate("The database was created");
            }
            case "migrate" -> {
                if(model != null){
                    RecordedClass<?> rclass = Recorder.getRecordedClass(model);
                    if(rclass != null){
                        if(Migration.migrate(rclass)){
                           Command.printTemplate("Model " + rclass.getName() + " is migrated successfully");
                        }else{
                            Command.printTemplate("Field to migrate a model " + rclass.getName(), "r");
                        }
                    }
                }else{
                    Migration.migrateAll();
                }
            }
            case "drop" -> {
                Migration.dropDatabase();
                Command.printTemplate("Database was dropped");
            }
        }
    }
}
