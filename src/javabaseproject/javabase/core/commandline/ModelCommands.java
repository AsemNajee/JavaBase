package javabaseproject.javabase.core.commandline;

import java.io.*;
import java.util.Scanner;
import javabaseproject.javabase.config.ENV;
import javabaseproject.model.Model;

public class ModelCommands extends Commands{
    private static final String PROJECT_PATH    = System.getProperty("user.dir") + File.separator + "src";
//    private static final String MODEL_PACKAGE   = getModelsPath();
    private static final String REGISTER_PATH   = getRegisterFilePath();

    public static void handle(String []args) throws IOException {
        if(args[0].equals("make:model")){
            makeModel(args);
        }
    }
    
    private static void makeModel(String []args) throws IOException{
        String modelName ;
        if(args.length < 2){ // < 3 if it : java JavaBase make:model
            System.out.print("Enter model name: ");
            modelName = new Scanner(System.in).next();
        }else{
            modelName = args[1];
        }
        if(!modelName.matches("^[A-Z][A-Za-z0-9]*$")){
            print("Model name not matches (^[A-Z][A-Za-z0-9]*$)");
            System.exit(0);
        }
        System.out.println(getPackagePathOfModel(modelName));
        File file = new File(getPackagePathOfModel(modelName));

        if (file.isFile()) {
            print("Model is already exists.");
        } else {
            if (createModel(modelName, file)) {
                registerModel(modelName);
                print("Model {" + modelName + "} created in " + file.getAbsolutePath());
            } else {
                print("Model Not Created");
            }
        }
    }

    private static String getPackagePathOfModel(String modelName){
        String localPath = Model.class.getName().replace(".Model", "").replace(".", File.separator);
        return PROJECT_PATH + File.separator + localPath + File.separator + modelName + ".java";
    }

    private static String getModelContent(String modelName){
        return """
                package {package}.model;
                               
                import {package}.javabase.core.annotations.PrimaryKey;
                import {package}.javabase.core.annotations.Unique;
                               
                @PrimaryKey("id")
                public class {Model} extends Model<{Model}>{
                               
                    protected int id;
                    @Unique
                    protected String name;
                    
                    public {Model}(){
                        super({Model}.class);
                    }
                               
                // ... add more props with protected access modifier
                }
                """.replace("{Model}", modelName)
                  .replace("{package}", ENV.DEFAULT_PACKAGE);
    }

    private static boolean createModel(String model, File file) throws IOException {
        if(file.createNewFile()){
            FileWriter fr = new FileWriter(file);
            fr.write(getModelContent(model));
            fr.close();
            return true;
        }
        return false;
    }

    private static void registerModel(String model) throws IOException{
        File file = new File(REGISTER_PATH);
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder fileContent = new StringBuilder();
        String temp;
        while((temp = br.readLine()) != null){
            fileContent.append(temp).append("\n");
        }
        br.close();
        int curserOfAddNewRegisterModel = fileContent.indexOf("//{NEW_MODEL_HERE}//");
        fileContent.insert(curserOfAddNewRegisterModel, "Recorder.add("+model+".class);\n\t");
        int curserOfAddNewImportForModel = fileContent.indexOf("//{NEW_IMPORT_HERE}//");
        fileContent.insert(curserOfAddNewImportForModel, "import {package}.model.".replace("{package}", ENV.DEFAULT_PACKAGE) + model + ";\n");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.append(fileContent);
        bw.close();
    }
    
    
    private static String getModelsPath(){
        return PROJECT_PATH + File.separator + ENV.DEFAULT_PACKAGE + File.separator + "model" + File.separator;
    }
    private static String getRegisterFilePath(){
        return PROJECT_PATH + File.separator + ENV.DEFAULT_PACKAGE + File.separator + "javabase" + File.separator + "MyModels.java";
    }
}