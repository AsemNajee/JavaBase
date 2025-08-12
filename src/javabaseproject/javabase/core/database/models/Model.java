package javabaseproject.javabase.core.database.models;

import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.recorder.Recorder;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author AsemNajee
 */
public class Model<T extends Model<T>> extends AbstractModel<T> {

    public boolean save() throws Exception{
        return super.save(this);
    }
    public boolean delete() throws SQLException, NoSuchFieldException, IllegalAccessException {
        return super.delete(this);
    }

    public static <R extends Model<R>> R find(Class<R> clazz, int id) throws SQLException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        var t = clazz.getDeclaredConstructor().newInstance();
        return (R) t.find(id);
    }

    public static <R extends Model<R>> ArrayList<R> getAll(Class<R> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException, NoSuchFieldException {
        var t = clazz.getDeclaredConstructor().newInstance();
        return t.getAll();
    }

    public String[] hidden(){
        return new String[]{};
    }
    public Factory factory(){
        return Recorder.getRecordedClass(this.getClass()).getFactory();
    }
    public Seeder seeder(){
        return Recorder.getRecordedClass(this.getClass()).getSeeder();
    }
    public static Model<? extends Model<?>> of(Class<? extends Model<?>> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.getDeclaredConstructor().newInstance();
    }
    public static Model<? extends Model<?>> of(String model) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (Model<? extends Model<?>>)Recorder.getRecordedClass(model).getClazz().getDeclaredConstructor().newInstance();
    }
}
