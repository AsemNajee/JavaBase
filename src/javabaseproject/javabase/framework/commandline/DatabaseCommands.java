package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.App;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.Migration;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.output.Colors;

public class DatabaseCommands extends Command{

    /**
     * @param model is optional and only will make different in db:migrate modelName and db:seed
     */
    public static void handle(String verb, String model) throws Exception {
        switch (verb){
            case "init" -> {
                Migration.initDatabase();
            }
            case "migrate" -> {
                if(model != null){
                    RecordedClass rclass = Recorder.getRecordedClass(model);
                    if(rclass != null){
                        if(Migration.migrate(rclass)){
                            printf("Model %s is migrated successfully", rclass.getName());
                        }else{
                            println("Field to migrate a model", Colors.RED);
                        }
                    }
                }else{
                    App.start(Migration::migrateAll);
                }
            }
            case "seed" -> {
                if(model != null){
                    App.start(() -> {
                        Model.of(model).seeder().run();
                    });
                }
            }
            case "drop" -> {
                App.start(Migration::dropDatabase);
            }
        }
    }
}
