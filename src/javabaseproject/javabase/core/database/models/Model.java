package javabaseproject.javabase.core.database.models;

import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.recorder.Recorder;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * the parent model of all your models
 * @author Asem Najee
 */
public class Model<T extends Model<T>> extends AbstractModel<T> {

    /**
     * save the data in the object into database
     * @return status of save
     */
    public boolean save() throws Exception{
        return super.save(this);
    }

    /**
     * delete the item in the database with the same of primary key
     * @return status of deleting
     */
    public boolean delete() throws SQLException, NoSuchFieldException, IllegalAccessException {
        return super.delete(this);
    }

    /**
     * find the data in the database
     * based on the primary key and the class of model
     * @param clazz the class of the model
     *              to create the object and store data in it
     * @param key the primary key to get its row from the database
     * @return object of {@code clazz} contains the data
     * @param <R> the result type
     */
    public static <R extends Model<R>> R find(Class<R> clazz, Object key) throws SQLException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        var t = clazz.getDeclaredConstructor().newInstance();
        return (R) t.find(key);
    }

    /**
     * get all rows in database based on the {@code clazz} parameter
     * @param clazz the class of the model to create collection of
     *              models contains the data retrieved from the database
     * @return collection of models of {@code clazz} class
     * @param <R> the result type
     */
    public static <R extends Model<R>> ArrayList<R> getAll(Class<R> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException, NoSuchFieldException {
        var t = clazz.getDeclaredConstructor().newInstance();
        return t.getAll();
    }

    /**
     * get the factory of this model
     * @return instance of factory of this model
     */
    public Factory factory(){
        return Recorder.getRecordedClass(this.getClass()).getFactory();
    }

    /**
     * get the Seeder of this model
     * @return instance of Seeder of this model
     */
    public Seeder seeder(){
        return Recorder.getRecordedClass(this.getClass()).getSeeder();
    }

    /**
     * get new instance of the model child
     * @param clazz type of the object will be returned
     * @return new instance of the object of {@code clazz}
     */
    public static Model<? extends Model<?>> of(Class<? extends Model<?>> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    /**
     * get new instance of the model child
     * @param model name of the class that the object will be returned
     * @return new instance of the object of {@code model} class
     */
    public static Model<? extends Model<?>> of(String model) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (Model<? extends Model<?>>)Recorder.getRecordedClass(model).getClazz().getDeclaredConstructor().newInstance();
    }
}
