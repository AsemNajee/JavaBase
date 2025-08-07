
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * helper to fast develop by the library    
 * @author Asem Abdullah Najee
 */

public class Asem {
    
    private static final String MODEL_PACKAGE = "src/reviewjdb/model/";
    private static final String REGISTER_PATH = "src/reviewjdb/jdbcmodel/MyModels.java";
    
    
    public static void print(String text){
        System.out.println(text);
    }
    public static void main(String[] args) throws IOException {
        if(args == null){
            
        }
        
        if(args[0].equals("make:model")){
            String modelName = args[1];
            File file = new File(getPackagePathOfModel(modelName));

            if(file.isFile()){
                print("Model is already exists.");
            }else{
                if(createModel(modelName, file)){
                    registerModel(modelName);
                    print("Model {" + modelName + "} created in " + file.getAbsolutePath());
                }
                else
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
    
    private static boolean createModel(String model, File file) throws IOException{
        if(file.createNewFile()){
            FileWriter fr = new FileWriter(file);
            fr.write(getModelContent(model));
            fr.close();
            return true;
        }
        return false;
    }
    
    private static boolean registerModel(String model) throws FileNotFoundException, IOException{
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
        return true;
    }
}


/**
 *  package reviewjdb.jdbcmodel;
 
import java.util.HashMap;
import reviewjdb.jdbcmodel.core.RecordedClass;
import reviewjdb.jdbcmodel.core.Recorder;
import reviewjdb.jdbcmodel.core.framework.MigrationsModel;
import reviewjdb.model.Car;
//{NEW_IMPORT_HERE}//
public class MyModels {    
    private static HashMap<String, RecordedClass> register(){        
        Recorder.add(MigrationsModel.class);
        Recorder.add(Car.class);		
        //{NEW_MODEL_HERE}//
        return Recorder.getModels();    
    } 
    public static HashMap<String, RecordedClass> getRegisteredModels(){        
        return register();
    }
}
 */