 package javabaseproject.javabase;
 
import java.util.HashMap;
import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.Recorder;

import javabaseproject.models.Book;
import javabaseproject.models.Car;
//{NEW_IMPORT_HERE}//
public class MyModels {    
    public static HashMap<String, RecordedClass> registerAll(){
	    Recorder.add(Car.class);
        Recorder.add(Book.class);
	    //{NEW_MODEL_HERE}//
        return Recorder.getModels();
    } 
    public static HashMap<String, RecordedClass> getRegisteredModels(){        
        return registerAll();
    }
}
