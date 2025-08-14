package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.App;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.Migration;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.output.Console;

public class DatabaseCommands extends Command{

    /**
     * @param model is optional and only will make different in db:migrate modelName and db:seed
     */
    public static void handle(String verb, String model) throws Exception {
        switch (verb){
            case "init" -> {
                Migration.initDatabase();
                Command.println("g[The database was created]");
            }
            case "migrate" -> {
                if(model != null){
                    RecordedClass rclass = Recorder.getRecordedClass(model);
                    if(rclass != null){
                        if(Migration.migrate(rclass)){
                           Command.printf("g[\tModel %s is migrated successfully\t]", rclass.getName());
                        }else{
                            Command.printf("Field to migrate a model {y{%s}}", rclass.getName());
                        }
                    }
                }else{
                    Migration.migrateAll();
                }
            }
            case "drop" -> {
                Migration.dropDatabase();
                Command.println("y{Database was dropped}");
            }
        }
    }
}
