package javabaseproject.javabase.core.database.io;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Fetcher {

    /**
     * fetch the result set to Object Model
     * @param result the result set from the database
     * @return object of the caller class filled with the data
     */
    public static <T extends Model<T>> T fetch(Class<T> clazz, ResultSet result) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T item;
        HashMap<String, RecordedClass.RecordedField> fields = Recorder.getRecordedClass(clazz).getFields();
        item = clazz.getDeclaredConstructor().newInstance();
        for(var field : fields.keySet()){
            Class<?> cls;
            if(fields.get(field).isParentField()) {
                cls = clazz.getSuperclass();
            }else{
                cls = clazz;
            }
            FieldController.set(cls.getDeclaredField(field), result, item);
        }
        return item;
    }
}
