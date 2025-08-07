/**
 *   >> Al-Reecha .~
 *   << BY : Asem Najee >>
 */
package reviewjdb.jdbcmodel.core;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import reviewjdb.jdbcmodel.core.RecordedClass.RecordedField;
import reviewjdb.model.Model;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Recorder {

    private static ArrayList<RecordedClass> models;
    private static HashMap<String, RecordedClass> modelsAsKeyValue;
    
    static {
        models = new ArrayList<>();
        modelsAsKeyValue = new HashMap<>();
    }

    public static HashMap<String, RecordedClass> getModels(){
        return modelsAsKeyValue;
    }
    
    /**
     * filter all class fields and class name to prepare it to contact with database
     * fields are stored in hash map like [name => object(RecordedField)]
     * this method is take the attributes from the class and its parent
     * @param clazz 
     * @return  
     */
    public static RecordedClass add(Class<? extends Model> clazz) {
        String splits[] = clazz.getName().split("\\.");
        RecordedClass cls = new RecordedClass(splits[splits.length - 1]);
        for(Field f : clazz.getDeclaredFields()){
            if(isFieldAcceptable(f)){
                RecordedField rf = filterField(f);
                cls.addField(rf.name, rf);
            }
        }
        for(Field f : clazz.getSuperclass().getDeclaredFields()){
            if(isFieldAcceptable(f)){
                RecordedField rf = filterField(f);
                cls.addField(rf.name, rf);
            }
        }
        models.add(cls);
        modelsAsKeyValue.put(clazz.getName(), cls);
        return cls;
    }

    /**
     * filter field attributes and return them as object of RecordedField
     * this method is filter the name and type of the field
     * @param field
     * @return 
     */
    private static RecordedField filterField(Field field) {
        RecordedField rf = new RecordedField();
        rf.name = field.getName();
        String[] pathToAnnotatinType = field.getAnnotatedType().toString().split("\\.");
        rf.type = switch(pathToAnnotatinType[pathToAnnotatinType.length -1]){
            case "boolean" -> Types.BOOLEAN;
            case "byte" -> Types.BYTE;
            case "short" -> Types.SHORT;
            case "int" -> Types.INT;
            case "long" -> Types.LONG;
            case "String" -> Types.STRING;
            default -> Types.STRING;
        };
        return rf;
    }
    
    /**
     * if and only if the field is int or string and private|protected
     * the field is acceptable, otherwise not
     * 
     * @param f
     * @return 
     */
    protected static boolean isFieldAcceptable(Field f){
        return f.getAnnotatedType().toString().matches("^[A-Za-z\\.]*(String|int|float|long|byte|short|boolean)$")
                && f.accessFlags().size() == 1
                && f.accessFlags().stream()
                        .filter(m -> m == AccessFlag.PRIVATE || m == AccessFlag.PROTECTED || true)
                        .count() == f.accessFlags().size();
    }
}
