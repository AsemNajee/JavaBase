package javabaseproject.javabase.core.database.models;

import java.lang.reflect.AccessFlag;
import java.sql.Connection;
import java.sql.SQLException;

import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.io.Fetcher;
import javabaseproject.javabase.core.database.io.Json;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.database.statements.ParameterFiller;
import javabaseproject.javabase.core.interfaces.Jsonable;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.Recorder;
import static javabaseproject.javabase.core.recorder.RecordedClass.RecordedField;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javabaseproject.javabase.core.database.statements.StatementBuilder;
import javabaseproject.javabase.core.recorder.Types;
import javabaseproject.javabase.framework.commandline.Command;

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
        T item;
        try (var result = DB.from(clazz).where(key).executeQuery()) {
            if(!result.next()){
                return null;
            }
            item = Fetcher.fetch(clazz, result);
        }
        return item;
    }

    /**
     * get all items from the database
     *
     * @return a collection of objects of the class which called this method
     */
    public ModelsCollection<T> getAll() throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        var stmt = StatementBuilder.getSelectQueryForAllItems(clazz);
        var result = stmt.executeQuery();
        ModelsCollection<T> list = new ModelsCollection<>();
        while(result.next()){
            list.add(Fetcher.fetch(clazz, result));
        }
        return list;
    }

    /**
     * save the data from the object into database
     *
     * @param model the object that contains the data,
     *              this is necessary because the method is accepting {@code Model}
     *              not {@code AbstractModel}, so we cannot use {@code this}
     * @return status of save operation
     */
    protected <M extends Model> boolean save(M model) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        if((boolean)FieldController.get(Model.class.getDeclaredField("isDatabase"), model)){
            return update(model);
        }
        var stmt = StatementBuilder.getInsertQueryForOneItem(model);
        stmt.executeUpdate();
        var generatedKeys = stmt.getGeneratedKeys();
        int key = 0;
        while(generatedKeys.next()){
                key = Integer.parseInt(String.valueOf(generatedKeys.getObject(1)));
        }
        var statement = StatementBuilder.getSelectQueryForItemWithKey(clazz, model.getKey());
        Object primaryKey;
        if(model.getKey() != null && !model.getKey().equals(0)){
            primaryKey = model.getKey();
        }else{
            primaryKey = key;
        }
        ParameterFiller.bindParam(statement, Recorder.getRecordedClass(clazz).getPrimaryKey().getType(), primaryKey, 1);
        var result = statement.executeQuery();
        if(result.next()){
            Fetcher.fetch(result, model);
        }
        return true;
    }

    protected <M extends Model<M>> boolean update(Model<M> model) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        var stmt = StatementBuilder.getUpdateQueryForOneItem(model);
        stmt.executeUpdate();
        return true;
    }

    /**
     * delete this item that has the same primary key with the object
     *
     * @param model is the object contains the data and primary key
     * @return status of deleting
     */
    protected boolean delete(Model<T> model) throws SQLException, NoSuchFieldException, IllegalAccessException {
        var stmt = StatementBuilder.getDeleteQueryForOneItem(model);
        int effectedRows = stmt.executeUpdate();
        return effectedRows == 1;
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
    public String toJson() {
        return toJson(0);
    }

    public String toJson(int level){
        String prefix = "\t".repeat(level);
        StringBuilder result = new StringBuilder(prefix).append("{");
        for(var fname : Recorder.getRecordedClass(this.clazz).getFields().keySet()){
            RecordedField rField = Recorder.getRecordedClass(clazz).getField(fname);
            try {
                Field field;
                if(rField.isParentField()){
                    field = this.clazz.getSuperclass().getDeclaredField(fname);
                }else{
                    field = this.clazz.getDeclaredField(fname);
                }
                if(isHidden(field)){
                    continue;
                }
                result.append("\n").append(prefix).append("\t\"").append(fname).append("\" : ");
                if(rField.getType() == Types.STRING){
                    String value = (String) FieldController.get(field, (Model<T>)this);
                    if(value == null){
                        result.append("null");
                    }else{
                        result.append("\"").append(value).append("\"");
                    }
                }else{
                    result.append((FieldController.get(field, (Model<T>)this)));
                }
                result.append(",");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        result.delete(result.length()-1, result.length());
        result.append("\n").append(prefix).append("}");
        return result.toString();
    }

    /**
     * get the model data from json text, </br>
     * this method is taken a json value as string and get the
     * model result from it
     * @param jsonText the json as string to get the data from
     * @return a model filled by data from json
     */
    public T fromJson(String jsonText) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Json json = new Json(jsonText);
        return json.getObject(clazz);
    }

    /**
     * get the value of the primary key
     */
    protected Object getKey(Model<T> model) throws NoSuchFieldException, IllegalAccessException {
        String keyName = Recorder.getRecordedClass(model.getClass()).getPrimaryKey().getName();
        return FieldController.get(keyName, model);
    }

    /**
     * check if the field is hidden, all private fields are hidden
     * @param field the field to check
     * @return status of hidden
     */
    public boolean isHidden(Field field){
        return field.accessFlags().contains(AccessFlag.PRIVATE);
    }
}
