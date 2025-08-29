package javabaseproject.javabase.core.database.models;

import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.recorder.Recorder;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * the parent model of all your models
 *
 * @author Asem Najee
 * @version 1.0
 */
public class Model<T extends Model<T>> extends AbstractModel<T> {
    /**
     * determine if the model is created from user of returned from database
     */
    private final boolean isDatabase = false;

    /**
     * save the data from the object into database
     *
     * @return status of save
     */
    public boolean save() throws Exception{
        return super.save(this);
    }

    /**
     * delete the item in the database with the same primary key value
     *
     * @return status of deleting
     */
    public boolean delete() throws Exception {
        return super.delete(this);
    }

    /**
     * find the data in the database
     * based on the primary key and the class of model
     *
     * @param clazz the class of the model to create the object
     *              and store data in it and get the data from its table
     * @param key the primary key to get its row from the database
     * @return new instance of {@code clazz} contains the data
     * @param <R> the result types
     */
    public static <R extends Model<R>> R find(Class<R> clazz, Object key) throws SQLException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        return Model.of(clazz).find(key);
    }

    /**
     * get all rows in database from the model table
     * based on the {@code clazz} parameter
     *
     * @param clazz the class of the model to create collection of
     *              models contains the data retrieved from the database
     * @return collection of models of {@code clazz} class
     * @param <R> the result type
     */
    public static <R extends Model<R>> ModelsCollection<R> getAll(Class<R> clazz) throws Exception {
        return Model.of(clazz).getAll();
    }

    /**
     * get the factory of this model
     *
     * @return instance of factory of this model
     */
    public Factory<T> factory(){
        return (Factory<T>) Recorder.getRecordedClass(this.getClass()).getFactory();
    }

    /**
     * get the Seeder of this model
     *
     * @return instance of Seeder of this model
     */
    public Seeder seeder(){
        return Recorder.getRecordedClass(this.getClass()).getSeeder();
    }

    /**
     * get new empty instance of the model child
     *
     * @param clazz type of the object will be returned
     * @return new instance of the object of {@code clazz}
     */
    public static <T extends Model<T>> T of(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    /**
     * get new empty instance of the model child
     *
     * @param model name of the class that the object will be returned
     * @return new instance of the object of {@code model} class
     */
    public static <T extends Model<T>> T of(String model) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return Recorder.<T>getRecordedClass(model).getClazz().getDeclaredConstructor().newInstance();
    }

    /**
     * get a query builder of {@link javabaseproject.javabase.core.database.querybuilders.query.DB}
     * to build a sql query and adding conditions and join and more
     *
     * @return instance of the query builder
     */
    public DB<T> query(){
        return DB.from(this.getClass());
    }

    /**
     * get the value of the primary key, this depends on the annotation
     * {@literal @PrimaryKey} in the model class
     *
     * @return value of the primary key
     */
    public Object getKey() {
        try {
            return super.getKey(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
