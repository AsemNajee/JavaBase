package javabaseproject.javabase.core;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import javabaseproject.javabase.core.RecordedClass.RecordedField;
import javabaseproject.javabase.core.annotations.NotNull;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.models.Model;

/**
 * 
 * @author AsemNajee
 */
public class Recorder {

    private static final HashMap<String, RecordedClass> modelsAsKeyValue;
    
    static {
        modelsAsKeyValue = new HashMap<>();
    }

    public static HashMap<String, RecordedClass> getModels(){
        return modelsAsKeyValue;
    }
    
    public static RecordedClass getRecordedClass(Class clazz){
        return modelsAsKeyValue.get(clazz.getName());
    }
    public static RecordedClass getRecordedClass(String modelName){
        for(var rc : getModels().values()){
            if(rc.getName().equals(modelName)){
                return rc;
            }
        }
        return null;
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
        filterAllFields(clazz, cls);
        filterAllFields(clazz.getSuperclass(), cls);
        setPrimaryKey(clazz, cls);
        modelsAsKeyValue.put(clazz.getName(), cls);
        return cls;
    }
    
    private static void filterAllFields(Class clazz, RecordedClass cls) {
        for(Field f : clazz.getDeclaredFields()){
            if(isFieldAcceptable(f)){
                RecordedField rf = filterField(f);
                cls.addField(rf.getName(), rf);
            }
        }
    }

    /**
     * filter field attributes and return them as object of RecordedField
     * this method is filter the name and type of the field
     * @param field
     * @return 
     */
    private static RecordedField filterField(Field field) {
        return new RecordedField(
                field.getName(),
                getFieldType(field),
                getFieldConstraints(field)
        );
    }
    
    private static void setPrimaryKey(Class<? extends  Model> clazz, RecordedClass cls){
        if(clazz.isAnnotationPresent(PrimaryKey.class)){
            cls.setPrimaryKey(cls.getField(clazz.getAnnotation(PrimaryKey.class).value()));
            cls.getField(cls.getPrimaryKey().getName()).addConstraint(Constraints.PRIMARY_KEY);
        }else{
            if(cls.getFields().containsKey("id")){
                cls.setPrimaryKey(cls.getField("id"));
            }
        }
    }
    
    private static Types getFieldType(Field field){
        String[] pathToAnnotatinType = field.getAnnotatedType().toString().split("\\.");
        return switch(pathToAnnotatinType[pathToAnnotatinType.length -1]){
                    case "boolean"  -> Types.BOOLEAN;
                    case "byte"     -> Types.BYTE;
                    case "short"    -> Types.SHORT;
                    case "int"      -> Types.INT;
                    case "long"     -> Types.LONG;
                    case "String"   -> Types.STRING;
                    default         -> Types.STRING;
                };
    }
    
    private static ArrayList<Constraints> getFieldConstraints(Field field){
        ArrayList<Constraints> constraints = new ArrayList<>();
        if(field.isAnnotationPresent(Unique.class)){
            constraints.add(Constraints.UNIQUE);
        }
        if(field.isAnnotationPresent(NotNull.class)){
            constraints.add(Constraints.NOT_NULL);
        }
        if(field.isAnnotationPresent(PrimaryKey.class)){
            constraints.add(Constraints.PRIMARY_KEY);
        }
//        if(field.isAnnotationPresent(ForeignKey.class)){
//            constraints.add(Constraints.FOREIGN_KEY);
//        }
        return constraints;
    }
    
    /**
     * if and only if the field is preemptive or string and protected
     * the field is acceptable, otherwise not
     * 
     * @param f
     * @return 
     */
    protected static boolean isFieldAcceptable(Field f){
        return f.getAnnotatedType().toString().matches("^[A-Za-z0-9.]*(String|int|float|long|byte|short|boolean)$")
                && f.accessFlags().size() == 1
                && f.accessFlags().stream()
                        .filter(m -> m == AccessFlag.PROTECTED)
                        .count() == f.accessFlags().size();
    }
}
