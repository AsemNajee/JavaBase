package javabaseproject.javabase.core.database.io;

import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.commandline.Command;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * fetching data from the result of the database query
 *
 * @version 1.0
 */
public class Fetcher {

    /**
     * fetch the result set to Object Model
     *
     * @param result the result set from the database
     * @return new instance of the model class filled with the data
     */
    public static <T extends Model<T>> T fetch(ResultSet result, T model) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class<T> clazz = (Class<T>) model.getClass();
        var metaData = metaData(result);
        HashMap<String, RecordedClass.RecordedField> fields = Recorder.getRecordedClass(clazz).getFields();
        Class<?> cls;
        for(var field : fields.keySet()){
            var realField = fields.get(field).getRealField();
//            if(fields.get(field).isParentField()) {
//                cls = clazz.getSuperclass();
//            }else{
//                cls = clazz;
//            }
//            Field realField = cls.getDeclaredField(field);
            if(metaData.containsKey(realField.getName())){
                FieldController.set(realField, result, model);
            }
        }
        FieldController.set(Model.class.getDeclaredField("isDatabase"), true, model);
        return model;
    }

    public static <T extends Model<T>> T fetch(Class<T> clazz, ResultSet result) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        return fetch(result, clazz.getDeclaredConstructor().newInstance());
    }

    public static <T extends Model<T>> ModelsCollection<T> fetchAll(Class<T> clazz, ResultSet result) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var models = new ModelsCollection<T>();
        while(result.next()){
            models.add(fetch(clazz, result));
        }
        return models;
    }

    /**
     * getting the metadata of the model , this mean the column names and the number of columns
     */
    public static HashMap<String, Integer> metaData(ResultSet result) throws SQLException {
        var map = new HashMap<String, Integer>();
        for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
            map.put(result.getMetaData().getColumnName(i), i);
        }
        return map;
    }
}
