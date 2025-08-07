 package reviewjdb.jdbcmodel;
 
import java.util.HashMap;
import reviewjdb.jdbcmodel.core.RecordedClass;
import reviewjdb.jdbcmodel.core.Recorder;
import reviewjdb.jdbcmodel.core.framework.MigrationsModel;
import reviewjdb.model.Car;
import reviewjdb.model.Bike;
import reviewjdb.model.Plan;
import reviewjdb.model.Asem;
//{NEW_IMPORT_HERE}//
public class MyModels {    
    private static HashMap<String, RecordedClass> register(){        
        Recorder.add(MigrationsModel.class);
        Recorder.add(Car.class);		
        Recorder.add(Bike.class);
        Recorder.add(Plan.class);
	Recorder.add(Asem.class);
	//{NEW_MODEL_HERE}//
        return Recorder.getModels();    
    } 
    public static HashMap<String, RecordedClass> getRegisteredModels(){        
        return register();
    }
}
