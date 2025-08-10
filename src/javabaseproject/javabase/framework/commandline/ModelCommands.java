package javabaseproject.javabase.framework.commandline;

import java.io.*;
import java.sql.SQLException;
import java.util.regex.Matcher;

import javabaseproject.javabase.config.ENV;
import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.Recorder;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.framework.FilePathes;
import javabaseproject.javabase.output.Colors;
import javabaseproject.javabase.output.Style;

public class ModelCommands extends Command{
    private static Matcher command;
    public static void handle(String commandVerb, String modelName, Matcher matcher) throws IOException, SQLException {
        command = matcher;
        switch (commandVerb){
            case "make" -> {
                makeModel(modelName);
            }
            case "drop" -> {
                dropModel(modelName, false);
            }
        }
    }
    
    private static void makeModel(String modelName) throws IOException{
        File file = new File(FilePathes.getModelPath(modelName));
        if (file.isFile()) {
            println("Model is already exists.", Colors.RED);
        } else {
            if (createModel(modelName, file)) {
                registerModel(modelName);
                if(command.group("factory") != null){
                    
                }
                println("Model {" + modelName + "} created in " + file.getAbsolutePath(), Colors.GREEN);
            } else {
                println("Model Not Created", Colors.RED);
            }
        }
    }

    public static void dropModel(String model, boolean force) throws IOException, SQLException {
        RecordedClass rclass = null;
        for(var rc : Recorder.getModels().values()){
            if(rc.getName().equals(model)){
                rclass = rc;
                break;
            }
        }
        if(rclass == null){
            println("Model name is wrong", Colors.YELLOW);
            return;
        }
        dropTableFromDB(rclass);
        unregisterModel(model);
        if(force){
            deleteModelJavaClass(model); // delete the model file from the project structure
        }
    }
    private static boolean dropTableFromDB(RecordedClass rclass) throws SQLException {
        return Connector.getConnection().createStatement().execute(Build.dropTable(rclass));
    }
    private static void unregisterModel(String model) throws IOException {
        String pattern = "(.*)" + model + "(;|.class)(.*)\n";
        String fileContent = readRegisterFile().toString();
        writeToRegisterFile(new StringBuilder(fileContent.replaceAll(pattern, "")));
        println("model dropped {" + Style.textColor(model, Colors.RED) + "} successfully");
    }
    private static void deleteModelJavaClass(String model) {
        String path = FilePathes.getModelPath(model);
        File file = new File(path);
        if(file.isFile()){
            file.delete();
        }
    }

    private static String getModelNewContent(String modelName, String key){
        return """
                package {package}.models;
                               
                import {package}.javabase.core.annotations.PrimaryKey;
                import {package}.javabase.core.annotations.Unique;
                               
                @PrimaryKey("{key}")
                public class {Model} extends Model<{Model}>{
                               
                    protected int {key};
                    @Unique
                    protected String name;
                    
                    public {Model}(){
                        super({Model}.class); // must call in every single constructor
                    }
                    
                   {factoyMethods}
                // ... add more props with protected access modifier
                }
                """.replace("{Model}", modelName)
                   .replace("{key}", modelName)
                   .replace("{package}", ENV.ROOT_PACKAGE);
    }

    private static boolean createModel(String model, File file) throws IOException {
        if(file.createNewFile()){
            FileWriter fr = new FileWriter(file);
            String key = "id";
            if(command.group("key") != null){
                key = command.group("key");
            }
            fr.write(getModelNewContent(model, key));
            fr.close();
            return true;
        }
        return false;
    }

    private static void registerModel(String model) throws IOException{
        StringBuilder fileContent = readRegisterFile();
        int curserOfAddNewRegisterModel = fileContent.indexOf("//{NEW_MODEL_HERE}//");
        fileContent.insert(curserOfAddNewRegisterModel, "Recorder.add("+model+".class);\n\t");
        int curserOfAddNewImportForModel = fileContent.indexOf("//{NEW_IMPORT_HERE}//");
        fileContent.insert(curserOfAddNewImportForModel, "import {package}.models.".replace("{package}", ENV.ROOT_PACKAGE) + model + ";\n");
        writeToRegisterFile(fileContent);
    }
    
    private static StringBuilder readRegisterFile() throws IOException {
        File file = new File(FilePathes.getRegisterFilePath());
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder fileContent = new StringBuilder();
        String temp;
        while((temp = br.readLine()) != null){
            fileContent.append(temp).append("\n");
        }
        br.close();
        return fileContent;
    }
    private static void writeToRegisterFile(StringBuilder fileContent) throws IOException {
        File file = new File(FilePathes.getRegisterFilePath());
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.append(fileContent);
        bw.close();
    }
}