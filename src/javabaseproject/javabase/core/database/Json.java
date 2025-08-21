package javabaseproject.javabase.core.database;

import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.commandline.Command;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Json {
    private HashMap<String, String> json;
    public Json(String jsonText){
        json = new HashMap<>();
        Pattern pattern = Pattern.compile("\"(?<key>[A-Za-z][A-Za-z0-9_$]*)\"( )*:( )*\"?(?<value>[^\",\n}]+)\"?");
        Matcher m = pattern.matcher(jsonText);
        while(m.find()){
            json.put(m.group("key"), m.group("value"));
        }
    }

    public HashMap<String, String> getAsHash(){
        return json;
    }

    public <T extends Model<T>> T getObject(Class<T> clazz) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        var fields = Recorder.getRecordedClass(clazz).getFields();
        var ins = clazz.getDeclaredConstructor().newInstance();
        for(var field : fields.keySet()){
            FieldController.set(User.class.getDeclaredField(field), getAsHash().get(field), ins);
        }
        return ins;
    }
}
