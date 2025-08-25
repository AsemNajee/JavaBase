package javabaseproject.javabase.core.database.io;

import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.Recorder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * fetch data from json to model instance
 *
 * @author AsemNajee
 * @version 1.0
 */
public class Json {
    /**
     * all key values from the json text is stored here
     */
    private final HashMap<String, String> json;

    /**
     * analyze the json text and change it to hashMap
     *
     * @param jsonText json data
     */
    public Json(String jsonText){
        json = new HashMap<>();
        Pattern pattern = Pattern.compile("\"(?<key>[A-Za-z][A-Za-z0-9_$]*)\"( )*:( )*\"?(?<value>[^\",\n}]+)\"?");
        Matcher m = pattern.matcher(jsonText);
        while(m.find()){
            json.put(m.group("key"), m.group("value"));
        }
    }

    /**
     * getter for hashMap
     *
     * @return result hasMap of the json
     */
    public HashMap<String, String> getAsHash(){
        return json;
    }

    /**
     * fill new instance of model by data from the hashMap and return it
     *
     * @param clazz the type of returned model
     * @return new instance filled by the data from the json
     */
    public <T extends Model<T>> T getObject(Class<T> clazz) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        var fields = Recorder.getRecordedClass(clazz).getFields();
        var ins = clazz.getDeclaredConstructor().newInstance();
        for(var field : fields.keySet()){
            FieldController.set(User.class.getDeclaredField(field), getAsHash().get(field), ins);
        }
        return ins;
    }
}
