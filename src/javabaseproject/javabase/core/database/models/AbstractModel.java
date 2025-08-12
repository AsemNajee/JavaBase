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
 * 
 * @author AsemNajee
 */
public abstract class AbstractModel<T extends Model<T>> {

    private final Class<? extends Model<?>> clazz;

    public abstract String[] hidden();

    protected AbstractModel(){
        this.clazz = (Class<? extends Model<?>>) this.getClass();
    }
    
    public T find(int id) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        T item;
        var stmt = StatementBuilder.getSelectQueryForItemWithId(clazz, id);
        var result = stmt.executeQuery();
        result.next();
        item = fetch(result);
        return item;
    }
    
    public ArrayList<T> getAll() throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        var stmt = StatementBuilder.getSelectQueryForAllItems(this.getClass());
        var result = stmt.executeQuery();
        ArrayList<T> list = new ArrayList<>();
        while(result.next()){
            list.add(fetch(result));
        }
        return list;
    }
    
    public boolean save(Model<T> model) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        var stmt = StatementBuilder.getInsertQueryForOneItem(model);
        int effectedRows = stmt.executeUpdate();
        return effectedRows == 1;
    }

    public boolean delete(Model<T> model) throws SQLException, NoSuchFieldException, IllegalAccessException {
        var stmt = StatementBuilder.getDeleteQueryForOneItem(model);
        int effectedRows = stmt.executeUpdate();
        return effectedRows == 1;
    }

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

    private String getThisModelTableName(){
        return Recorder.getRecordedClass(this.getClass()).getName();
    }
    
    private boolean isParentsField(String fieldName){
        Field[] fieldsInParent = clazz.getSuperclass().getDeclaredFields();
        for (Field f : fieldsInParent) {
            if (f.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public boolean dropTable() throws Exception {
        return DBMS.execute(Build.dropTable(Recorder.getRecordedClass(clazz)));
    }

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

    public boolean isHidden(Field field){
        for (var hidden : hidden()){
            if(field.getName().equals(hidden)){
                return true;
            }
        }
        return false;
    }
}
