 package javabaseproject.javabase;
 
import java.util.HashMap;
import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.Recorder;
import javabaseproject.model.Book;
import javabaseproject.model.Person;
import javabaseproject.model.Student;
import javabaseproject.model.Test;
import javabaseproject.model.TestModel;
//{NEW_IMPORT_HERE}//
public class MyModels {    
    public static HashMap<String, RecordedClass> registerAll(){
        Recorder.add(Book.class);
	Recorder.add(TestModel.class);
	//{NEW_MODEL_HERE}//
        return Recorder.getModels();    
    } 
    public static HashMap<String, RecordedClass> getRegisteredModels(){        
        return registerAll();
    }
}
