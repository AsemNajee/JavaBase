package javabaseproject.javabase.core.commandline;

import java.io.*;
import java.util.Scanner;

public class ModelCommands extends Commands{
    private static final String MODEL_PACKAGE = "src/reviewjdb/model/";
    private static final String REGISTER_PATH = "src/reviewjdb/javabase/MyModels.java";

    public static void handle(String []args) throws IOException {
        if(args[0].equals("make:model")){
            makeModel(args);
        }
    }
    
    private static void makeModel(String []args) throws IOException{
        String modelName ;
        if(args.length < 3){
            modelName = new Scanner(System.in).next();
        }else{
            modelName = args[1];
        }
        if(!modelName.matches("^[A-Z][A-Za-z0-9]*$")){
            print("Model name not matches (^[A-Z][A-Za-z0-9]*$)");
            System.exit(0);
        }
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
        return MODEL_PACKAGE + modelName + ".java";
    }

    private static String getModelContent(String modelName){
        return """
               package reviewjdb.model;
               
               public class {Model} extends Model<{Model}>{
                   
                   public {Model}(){
                       super({Model}.class);
                   }
               
               // ... write your code here
               }
               """.replace("{Model}", modelName);
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
        fileContent.insert(curserOfAddNewImportForModel, "import reviewjdb.model." + model + ";\n");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.append(fileContent);
        bw.close();
    }
}