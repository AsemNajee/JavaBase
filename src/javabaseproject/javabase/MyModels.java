 package javabaseproject.javabase;
 
import java.util.HashMap;
import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.Recorder;
import javabaseproject.javabase.core.framework.MigrationsModel;
import javabaseproject.model.Car;
import javabaseproject.model.Bike;
import javabaseproject.model.Plan;
import javabaseproject.model.Asem;
import javabaseproject.model.Book;
import javabaseproject.model.Person;
import javabaseproject.model.Student;
//{NEW_IMPORT_HERE}//
public class MyModels {    
    private static HashMap<String, RecordedClass> register(){        
//        Recorder.add(MigrationsModel.class);
        Recorder.add(Student.class);		
        Recorder.add(Book.class);		
        Recorder.add(Person.class);
//        Recorder.add(Plan.class);
//	Recorder.add(Asem.class);
	//{NEW_MODEL_HERE}//
        return Recorder.getModels();    
    } 
    public static HashMap<String, RecordedClass> getRegisteredModels(){        
        return register();
    }
}
