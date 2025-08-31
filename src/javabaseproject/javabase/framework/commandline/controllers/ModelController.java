package javabaseproject.javabase.framework.commandline.controllers;

import javabaseproject.ENV;
import javabaseproject.javabase.Register;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;
import javabaseproject.javabase.framework.commandline.output.Style;
import javabaseproject.javabase.framework.generators.ModelGenerator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ModelController {

    public static void make(String modelName, boolean withFactory, String key, String keyType) throws IOException {
        String path;
        if((path = createModelFile(modelName, key, keyType)) != null) {
            if (withFactory) {
                FactoryController.make(modelName, true);
                SeederController.make(modelName);
            }
            Command.printTemplate(
                    "Model Created",
                    "Model " + modelName + " created in \n" + path,
                    "g");
        }
    }

    public static void drop(String model, boolean deleteModelFile) throws IOException, SQLException {
        RecordedClass rclass = Recorder.getRecordedClass(model);
        if(rclass == null){
            Command.printTemplate(
                    "Model is not found",
                    "r");
            return;
        }
        dropModelTable(rclass);
        if(deleteModelFile){
            deleteModelFile(model); // delete the model file from the project structure
        }
        Register.getModels().remove(model);
        Command.printTemplate("model dropped " + model + "successfully");
    }

    /**
     * create model java file in models package
     *
     * @param modelName name of the model to create
     * @param key the name of primary key in the model, default is {@code id}
     * @param keyType type of primary key, default is {@code int}
     */
    private static String createModelFile(String modelName, String key, String keyType) throws IOException {
        File file = new File(FilePaths.getModelPath(modelName));
        if (file.isFile()) {
            Command.printTemplate(
                    "Model is already exists.",
                    "r");
            return null;
        }
        if(key == null){
            key = ENV.DEFAULT_PRIMARY_KEY;
        }
        if(keyType == null){
            keyType = "int";
        }
        FileHandler.of(file).write(new ModelGenerator(modelName, key, keyType).modelFile());
        return file.getAbsolutePath();
    }

    /**
     * delete table of the model from the database
     *
     * @param rclass the class of the model
     * @throws SQLException
     */
    private static void dropModelTable(RecordedClass rclass) throws SQLException {
        Connector.getConnection().createStatement().execute(Build.dropTable(rclass));
    }

    /**
     * delete the java file from models package
     *
     * @param model the model name which will its java class be deleted
     */
    private static void deleteModelFile(String model) {
        String path = FilePaths.getModelPath(model);
        File file = new File(path);
        if(file.isFile()){
            file.delete();
        }
    }

    /**
     * add new method to the end of the model
     *
     * @param modelName the name of the model
     * @param methodImplements the method implementation as string
     */
    public static void addNewMethodToModel(String modelName, String methodImplements) throws IOException {
        FileHandler file = FileHandler.of(FilePaths.getModelPath(modelName));
        if(!file.exists()){
            Command.printTemplate(
                    "Model " + modelName + "not found",
                    "r");
            return;
        }
        String content = file.read();
        // adding the method to the end of the class, before closing the bracket
        content = content.replaceAll("(?s)(?<cDef>.*class(.*)Model<.*>\\{)(?<content>.*)}", "${cDef}${content}" + methodImplements + "\n}");
        if(!content.matches(".*import .*ModelsCollection;")){
            content = content.replaceFirst("import", "import " + ENV.ROOT_PACKAGE + ".javabase.core.collections.ModelsCollection;\nimport");
        }
        file.write(content);
    }
}
