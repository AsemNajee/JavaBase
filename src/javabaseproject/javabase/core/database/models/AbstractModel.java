package javabaseproject.javabase.core.database.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.Recorder;
import static javabaseproject.javabase.core.recorder.RecordedClass.RecordedField;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javabaseproject.javabase.core.database.DBMS;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.core.database.statements.StatementBuilder;

/**
 * Abstract model is the core class in this framework
 * this class is interacting with the database and models to easily the relation between them
 * @author Asem Najee
 */
public abstract class AbstractModel<T extends Model<T>> {

    /**
     * the class of the model that own the instance
     */
    private final Class<? extends Model<?>> clazz;

    /**
     * hidden fields from the database
     * this method is set the fields which is security and may not be shown
     * <h2>Example</h2>
     * in your application if you have model for users (id, name, email, password)
     * the password field is secure, and you should not show it in any case
     * you must add it to the hidden in your model
     * <br />
     * {@code Users.java}
     * <br />
     * <pre>
     * <code>
     * public String[] hidden(){
     *    return new String []{
     *        "password"
     *      };
     *  }
     * </code>
     * </pre>
     * @return array of string contains the hidden fields
     */
    public abstract String[] hidden();

    protected AbstractModel(){
        this.clazz = (Class<? extends Model<?>>) this.getClass();
    }

    /**
     * find item in the database by the primary key
     * @param key the primary key value
     * @return new instance of the model how called it, filled by data
     */
    public T find(Object key) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        T item;
        var stmt = StatementBuilder.getSelectQueryForItemWithKey(clazz, key);
        var result = stmt.executeQuery();
        result.next();
        item = fetch(result);
        return item;
    }

    /**
     * get all items from the database
     * @return a collection of objects of the class which called this method
     */
    public ArrayList<T> getAll() throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        var stmt = StatementBuilder.getSelectQueryForAllItems(this.getClass());
        var result = stmt.executeQuery();
        ArrayList<T> list = new ArrayList<>();
        while(result.next()){
            list.add(fetch(result));
        }
        return list;
    }

    /**
     * save the data in the object into database
     * @param model the object that contains the data,
     *              this is necessary because the method is accepting {@code Model}
     *              not {@code AbstractModel}, so we cannot use {@code this}
     * @return status of save operation
     */
    public boolean save(Model<T> model) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        var stmt = StatementBuilder.getInsertQueryForOneItem(model);
        int effectedRows = stmt.executeUpdate();
        return effectedRows == 1;
    }

    /**
     * delete this item that has the same primary key with the object
     * @param model is the object contains the data and primary key
     * @return status of deleting
     */
    public boolean delete(Model<T> model) throws SQLException, NoSuchFieldException, IllegalAccessException {
        var stmt = StatementBuilder.getDeleteQueryForOneItem(model);
        int effectedRows = stmt.executeUpdate();
        return effectedRows == 1;
    }

    /**
     * fetch the result set to Object
     * @param result the result set from the database
     * @return object of the caller class filled with the data
     */
    private T fetch(ResultSet result) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        T item;
        HashMap<String, RecordedField> fields = Recorder.getModels().get(this.getClass().getName()).getFields();
        item = (T) clazz.getDeclaredConstructor().newInstance();
        for(var field : fields.keySet()){
            Class cls;
            if(isParentsField(field)){
                cls = clazz.getSuperclass();
            }else{
                cls = clazz;
            }
            FieldController.set(cls.getDeclaredField(field), fields.get(field), result, item);
        }
        return item;
    }

    /**
     * check if the field is from the parent
     * this help in inheritance
     * this method is not implemented
     * @param fieldName the field name
     * @return if the field is from parent
     */
    private boolean isParentsField(String fieldName){
        Field[] fieldsInParent = clazz.getSuperclass().getDeclaredFields();
        for (Field f : fieldsInParent) {
            if (f.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * change the object from object to string with style like json
     * helps if you want to save the data in json file
     * oops, there is no way to retrieve data from json to object :(
     * you can help by create a pull request
     * @return string of data as json
     */
    public String toJson() {
        StringBuilder result = new StringBuilder("{");
        for(var field : clazz.getDeclaredFields()){
            try {
                field.setAccessible(true);
                result.append("\n\t").append(field.getName()).append(":").append(field.get(this)).append(",");
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        result.delete(result.length()-1, result.length());
        result.append("\n}");
        return result.toString();
    }

    /**
     * check if the field is hidden from {@link this.hidden()}
     * @param field the field to check
     * @return status of hidden
     */
    public boolean isHidden(Field field){
        for (var hidden : hidden()){
            if(field.getName().equals(hidden)){
                return true;
            }
        }
        return false;
    }
}
