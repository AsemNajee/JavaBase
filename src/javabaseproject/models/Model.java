package javabaseproject.models;

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

}
