 package javabaseproject.javabase;
 
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javabaseproject.database.models.Person;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;

import javabaseproject.database.models.User;
//{NEW_IMPORT_HERE}//
public class MyModels {
    private static boolean flag;
    private static HashMap<String, RecordedClass> registerAll() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
	Recorder.add(User.class);
    Recorder.add(Person.class);
	//{NEW_MODEL_HERE}//
        return Recorder.getModels();
    }
    public static HashMap<String, RecordedClass> getRegisteredModels() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(flag){
            return Recorder.getModels();
        }
        return registerAll();
    }
}
