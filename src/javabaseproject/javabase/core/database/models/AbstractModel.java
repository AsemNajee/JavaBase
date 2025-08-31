package javabaseproject.javabase.core.database.models;

import java.sql.SQLException;

import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.interfaces.Jsonable;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.Recorder;
import java.lang.reflect.InvocationTargetException;

/**
 * Abstract model is the core class in this framework
 * this class is interacting with the database and models to easily the relation between them
 *
 * @author Asem Najee
 * @version 1.0
 */
public abstract class AbstractModel<T extends Model<T>> implements Jsonable {

    /**
     * the class of the model that own the instance
     */
    private final Class<T> clazz;

    protected AbstractModel(){
        this.clazz = (Class<T>) this.getClass();
    }

    /**
     * find item in the database by the primary key
     *
     * @param key the primary key value
     * @return new instance of the model how called it, filled by data
     */
    public T find(Object key) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        return DB.from(clazz).where(key).get();
    }

    /**
     * get all items from the database
     *
     * @return a collection of objects of the class which called this method
     */
    public ModelsCollection<T> getAll() throws Exception {
        return DB.from(clazz).all();
    }

    /**
     * save the data from the object into database
     *
     * @param model the object that contains the data,
     *              this is necessary because the method is accepting {@code Model}
     *              not {@code AbstractModel}, so we cannot use {@code this}
     * @return status of save operation
     */
    protected <M extends Model> boolean save(M model) throws Exception {
        if((boolean)FieldController.get(Model.class.getDeclaredField("isDatabase"), model)){
            return update(model) == 1;
        }else{
            return DB.insert(model) != null;
        }
    }

    protected <M extends Model> int update(M model) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        return DB.from(model.getClass()).update(model);
    }

    /**
     * delete this item that has the same primary key with the object
     *
     * @param model is the object contains the data and primary key
     * @return status of deleting
     */
    protected boolean delete(Model<T> model) throws Exception {
        return !DB.from(clazz).where(model.getKey()).delete().isEmpty();
    }

    /**
     * change the object from object to string with style like json
     * helps if you want to save the data in json file
     * oops, there is no way to retrieve data from json to object :(
     * you can help by create a pull request
     *
     * @return string of data as json
     */
    @Override
    public abstract String toJson();
    @Override
    public abstract String toJson(int level);

    /**
     * get the value of the primary key
     */
    protected Object getKey(Model<T> model) throws NoSuchFieldException, IllegalAccessException {
        String keyName = Recorder.getRecordedClass(model.getClass()).getPrimaryKey().getName();
        return FieldController.get(keyName, model);
    }
}
