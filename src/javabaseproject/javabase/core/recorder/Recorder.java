package javabaseproject.javabase.core.recorder;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javabaseproject.javabase.core.annotations.*;
import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.recorder.RecordedClass.RecordedField;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.commandline.Command;

/**
 * record all models in the project from the package of models
 * in {@link javabaseproject.ENV#MODELS_PACKAGE }
 * @author AsemNajee
 */
public class Recorder {
    /**
     * store temporarily fields having foreign keys to
     * firstly register models then register foreign keys
     */
    private static final List<RecordedField> fieldsWithForeignKeys;

    /**
     * record models as key value, the key is the model name as full package
     * the value is model but after filtering and recorded as {@link RecordedClass}
     */
    private static final HashMap<String, RecordedClass<? extends Model<?>>> modelsAsKeyValue;
    
    static {
        fieldsWithForeignKeys = new LinkedList<>();
        modelsAsKeyValue = new HashMap<>();
    }

    /**
     * getting all registered models
     * @return hashMap of registered models
     */
    public static HashMap<String, RecordedClass<? extends Model<?>>> getModels(){
        return modelsAsKeyValue;
    }

    /**
     * get recorded model as {@link RecordedClass}
     * @param clazz the class of the model
     * @return a recorded class model
     */
    public static RecordedClass<? extends Model<?>> getRecordedClass(Class<?> clazz){
        return modelsAsKeyValue.get(clazz.getName());
    }

    /**
     * get recorded model as {@link RecordedClass}
     * @param modelName the name of the model
     * @return a recorded class model
     */
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

    /**
     * filtering all model fields from model class to register the model
     * and save the as {@link RecordedField}
     * @param clazz the model class
     * @param cls the model as {@link RecordedClass} to save fields in it
     * @param isParent if the fields from the parent of the model
     *                 this help to set and get the value from the field
     *                 using reflection without errors
     */
    private static <M extends Model<M>> void filterAllFields(Class<M> clazz, RecordedClass<M> cls, boolean isParent) {
        for(Field f : clazz.getDeclaredFields()){
            if(isFieldAcceptable(f)){
                RecordedField rf = filterField(f, isParent);
                cls.addField(rf.getName(), rf);
            }
        }
    }

    /**
     * filter field attributes and return them
     * as object of {@link RecordedField}</br>
     * this method is filter the name, type, constraints, ownerClass and flags
     */
    private static RecordedField filterField(Field field, boolean isFromParent) {
        var rField =  new RecordedField(
                field.getName(),
                FieldController.getFieldType(field),
                getFieldConstraints(field),
                field,
                isFromParent,
                field.accessFlags().contains(AccessFlag.PRIVATE)
        );
        if(field.isAnnotationPresent(ForeignKey.class)){
            fieldsWithForeignKeys.add(rField);
        }
        return rField;
    }

    /**
     * adding primary key constraint to the recorded class
     * @param clazz the model class
     * @param cls the recorded model to save a key in it
     */
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

    /**
     * get all constraint for the field and register them
     * @param field the field in the model class to get annotations of it
     * @return list of constraints of the field
     */
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
        if(field.isAnnotationPresent(AutoIncrement.class)){
            constraints.add(Constraints.AUTO_INCREMENT);
        }
        return constraints;
    }
    
    /**
     * if and only if the field is preemptive or string
     * and protected or private without any more flags
     * the field is acceptable, otherwise not </br>
     * the field will not register if it's not acceptable
     *
     * @param f the field to check
     * @return acceptability of the field
     */
    protected static boolean isFieldAcceptable(Field f){
        return f.getAnnotatedType().toString().matches("^[A-Za-z0-9.]*(String|int|float|long|byte|short|boolean)$")
                && f.accessFlags().size() == 1 // only private or protected only and only without anymore access flags
                && f.accessFlags().stream()
                        .filter(m -> m == AccessFlag.PROTECTED || m == AccessFlag.PRIVATE)
                        .count() == f.accessFlags().size();
    }

    /**
     * start register all foreign keys after recording all models
     */
    public static void registerForeignKeys(){
        for (var field : fieldsWithForeignKeys){
            Field realField = field.getRealField();
            var foreignModel = realField.getAnnotation(ForeignKey.class).value();
            field.references(foreignModel);
        }
    }
}
