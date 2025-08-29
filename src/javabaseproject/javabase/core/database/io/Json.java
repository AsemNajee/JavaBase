package javabaseproject.javabase.core.database.io;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.recorder.Types;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
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
public class Json<M extends Model<M>> {
    /**
     * all key values from the json text is stored here
     */
    private final HashMap<String, String> json;
    private final Class<M> clazz;
    private Model<M> model;

    /**
     * analyze the json text and change it to hashMap
     *
     * @param jsonText json data
     */
    public Json(Class<M> clazz, String jsonText){
        json = new HashMap<>();
        Pattern pattern = Pattern.compile("\"(?<key>[A-Za-z][A-Za-z0-9_$]*)\"( )*:( )*\"?(?<value>[^\",\n}]+)\"?");
        Matcher m = pattern.matcher(jsonText);
        while(m.find()){
            json.put(m.group("key"), m.group("value"));
        }
        this.clazz = clazz;
        try {
            this.model = getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Json(Model<M> model) throws IllegalAccessException {
        json = new HashMap<>();
        clazz = (Class<M>) model.getClass();
        var rclass = Recorder.getRecordedClass(clazz);
        for (var field : rclass.getFields().keySet()) {
            json.put(field, String.valueOf(FieldController.get(rclass.getField(field).getRealField(), model)));
        }
        this.model = model;
    }

    /**
     * getter for hashMap
     *
     * @return result hasMap of the json
     */
    public HashMap<String, String> getAsMap(){
        return json;
    }

    /**
     * fill new instance of model by data from the hashMap and return it
     *
     * @return new instance filled by the data from the json
     */
    public Model<M> getObject() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        if(model != null){
            return model;
        }
        var rclass = Recorder.getRecordedClass(clazz);
        var fields = rclass.getFields();
        var ins = clazz.getDeclaredConstructor().newInstance();
        for(var fieldName : fields.keySet()){
            RecordedClass.RecordedField rField = rclass.getField(fieldName);
            Field field = rField.getRealField();
            if(field.accessFlags().contains(AccessFlag.PRIVATE)){
                continue;
            }
            if(getAsMap().containsKey(fieldName)){
                FieldController.set(fields.get(fieldName).getRealField(), getAsMap().get(fieldName), ins);
            }else{
                FieldController.setEmpty(fields.get(fieldName).getRealField(), ins);
            }
        }
        model = ins;
        return model;
    }

    public <T extends Model<T>> String toJson(int level) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        model = getObject();
        String prefix = "\t".repeat(level);
        StringBuilder result = new StringBuilder(prefix).append("{");
        var rclass = Recorder.getRecordedClass(clazz);
        for (var key : json.keySet()) {
            result.append("\n").append(prefix).append("\t\"").append(key).append("\"").append(" : ");
            if(rclass.getField(key).getType() == Types.STRING){
                result.append("\"").append(json.get(key)).append("\"");
            }else{
                result.append(json.get(key));
            }
            result.append(",");
        }
        result.deleteCharAt(result.length()-1);
        result.append("\n").append(prefix).append("}");
        return result.toString();
    }
}
