package javabaseproject.javabase.framework.commandline.controllers;

import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.RegisterController;
import javabaseproject.javabase.framework.commandline.output.Colors;
import javabaseproject.javabase.framework.commandline.output.Style;
import javabaseproject.javabase.framework.readycontent.NewContent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ModelController {

    public static void make(String modelName, boolean withFactory, String key, String keyType) throws IOException {
        String path;
        if((path = createModelFile(modelName, key, keyType)) != null) {
            RegisterController.register(modelName);
            if (withFactory) {
                FactoryController.make(modelName);
            }
            Command.println("Model {" + modelName + "} created in " + path, Colors.GREEN);
        }
    }

    public static void drop(String model, boolean deleteModelFile) throws IOException, SQLException {
        RecordedClass rclass = Recorder.getRecordedClass(model);
        if(rclass == null){
            Command.println("Model name is wrong", Colors.YELLOW);
            return;
        }
        dropModelTable(rclass);
        RegisterController.unregister(model);
        if(deleteModelFile){
            deleteModelFile(model); // delete the model file from the project structure
        }
        Recorder.getModels().remove(model);
        Command.println("model dropped {" + Style.textColor(model, Colors.RED) + "} successfully");
    }

    /**
     * create model java file in models package
     * @param modelName name of the model to create
     * @param key the name of primary key in the model, default is {@code id}
     * @param keyType type of primary key, default is {@code int}
     * @throws IOException
     */
    private static String createModelFile(String modelName, String key, String keyType) throws IOException {
        File file = new File(FilePaths.getModelPath(modelName));
        if (file.isFile()) {
            Command.println("Model is already exists.", Colors.RED);
            return null;
        }
        if(key == null){
            key = "id";
        }
        if(keyType == null){
            keyType = "int";
        }
        FileHandler.of(file).write(NewContent.modelContent(modelName, key, keyType));
        return file.getAbsolutePath();
    }

    /**
     * delete table of the model from the database
     * @param rclass the class of the model
     * @throws SQLException
     */
    private static void dropModelTable(RecordedClass rclass) throws SQLException {
        Connector.getConnection().createStatement().execute(Build.dropTable(rclass));
    }

    /**
     * delete the java file from models package
     * @param model the model name which will its java class be deleted
     */
    private static void deleteModelFile(String model) {
        String path = FilePaths.getModelPath(model);
        File file = new File(path);
        if(file.isFile()){
            file.delete();
        }
    }
}
