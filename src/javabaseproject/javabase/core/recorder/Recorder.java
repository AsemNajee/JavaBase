package javabaseproject.javabase.core.recorder;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.recorder.RecordedClass.RecordedField;
import javabaseproject.javabase.core.annotations.NotNull;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;

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
     * fields are stored in hash map like <b>[name => object(RecordedField)]</b>
     * <br />
     * this method is taking the attributes from the class and its parent
     * <br />
     * also its register the factory and seeder from {@code FilePaths.get[Seeder|Factory]Package()}
     * @param clazz the class will be registered
     * @return class information after registering
     */
    public static RecordedClass add(Class<? extends Model<?>> clazz) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String modelName = clazz.getName().replaceAll("(.*)\\.", "");
        RecordedClass cls = new RecordedClass(modelName, clazz);
        if(FileHandler.of(FilePaths.getSeederPath(modelName)).exists()){
            cls.setSeeder((Seeder) Class.forName(FilePaths.getSeedersPackage() + "." + modelName + "Seeder" ).getConstructor().newInstance());
        }
        if(FileHandler.of(FilePaths.getFactoryPath(modelName)).exists()){
            cls.setFactory((Factory) Class.forName(FilePaths.getFactoriesPackage() + "." + modelName + "Factory" ).getConstructor().newInstance());
        }
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
                FieldController.getFieldType(field),
                getFieldConstraints(field)
        );
    }
    
    private static void setPrimaryKey(Class<? extends  Model<?>> clazz, RecordedClass cls){
        if(clazz.isAnnotationPresent(PrimaryKey.class)){
            cls.setPrimaryKey(cls.getField(clazz.getAnnotation(PrimaryKey.class).value()));
            cls.getField(cls.getPrimaryKey().getName()).addConstraint(Constraints.PRIMARY_KEY);
        }else{
            if(cls.getFields().containsKey("id")){
                cls.setPrimaryKey(cls.getField("id"));
            }
        }
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
