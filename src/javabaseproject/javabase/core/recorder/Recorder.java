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

    private static final HashMap<String, RecordedClass<? extends Model<?>>> modelsAsKeyValue;
    
    static {
        modelsAsKeyValue = new HashMap<>();
    }

    public static HashMap<String, RecordedClass<? extends Model<?>>> getModels(){
        return modelsAsKeyValue;
    }

    public static <M extends Model<M>> RecordedClass<M> getRecordedClass(Class<M> clazz){
        return (RecordedClass<M>) modelsAsKeyValue.get(clazz.getName());
    }
    public static <M extends Model<M>> RecordedClass<M> getRecordedClass(String modelName){
        for(var rc : getModels().values()){
            if(rc.getName().equals(modelName)){
                return (RecordedClass<M>) rc;
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
    public static <M extends Model<M>> RecordedClass<M> add(Class<M> clazz) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String modelName = clazz.getName().replaceAll("(.*)\\.", "");
        RecordedClass<M> cls = new RecordedClass<>(modelName, clazz);
        if(FileHandler.of(FilePaths.getSeederPath(modelName)).exists()){
            cls.setSeeder((Seeder) Class.forName(FilePaths.getSeedersPackage() + "." + modelName + "Seeder" ).getConstructor().newInstance());
        }
        if(FileHandler.of(FilePaths.getFactoryPath(modelName)).exists()){
            cls.setFactory((Factory) Class.forName(FilePaths.getFactoriesPackage() + "." + modelName + "Factory" ).getConstructor().newInstance());
        }
        filterAllFields(clazz, cls, false);
        if(!clazz.getSuperclass().isNestmateOf(Model.class)){
            filterAllFields((Class<M>) clazz.getSuperclass(), cls, true);
        }
        setPrimaryKey(clazz, cls);
        modelsAsKeyValue.put(clazz.getName(), cls);
        return cls;
    }
    
    private static <M extends Model<M>> void filterAllFields(Class<M> clazz, RecordedClass<M> cls, boolean isParent) {
        for(Field f : clazz.getDeclaredFields()){
            if(isFieldAcceptable(f)){
                RecordedField rf = filterField(f, isParent);
                cls.addField(rf.getName(), rf);
            }
        }
    }

    /**
     * filter field attributes and return them as object of RecordedField
     * this method is filter the name and type of the field
     *
     */
    private static RecordedField filterField(Field field, boolean isFromParent) {
        return new RecordedField(
                field.getName(),
                FieldController.getFieldType(field),
                getFieldConstraints(field),
                isFromParent,
                field.accessFlags().contains(AccessFlag.PRIVATE)
        );
    }
    
    private static <M extends Model<M>> void setPrimaryKey(Class<M> clazz, RecordedClass<M> cls){
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
                && f.accessFlags().size() == 1 // only private or protected only and only without anymore access flags
                && f.accessFlags().stream()
                        .filter(m -> m == AccessFlag.PROTECTED || m == AccessFlag.PRIVATE)
                        .count() == f.accessFlags().size();
    }
}
