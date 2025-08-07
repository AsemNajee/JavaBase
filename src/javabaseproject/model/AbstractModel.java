 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package javabaseproject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javabaseproject.javabase.core.Recorder;
import static javabaseproject.javabase.core.RecordedClass.RecordedField;
import javabaseproject.javabase.core.database.Connector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javabaseproject.javabase.core.database.quarybuilders.Build;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public abstract class AbstractModel<T> {
    
    public abstract T find(int id) throws Exception;
    public abstract ArrayList<T> getAll() throws Exception;
    public abstract boolean save() throws Exception;
    
    
    
    protected T find(int id, Class clazz) throws SQLException, IllegalArgumentException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        T item;
        var stmt = this.getSelectQuaryForItemWithId(id);
        var result = stmt.executeQuery();
        item = fetch(result, clazz).get(id);
        return item;
    }
    
    protected ArrayList<T> getAll(Class clazz) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        var stmt = this.getSelectQuaryForAllItems();
        var result = stmt.executeQuery();
        return fetch(result, clazz);
    }
    
    protected boolean save(Model model) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        getInsertQuaryForOneItem(model);
        return true;
    }
    
    
    protected ArrayList<T> fetch(ResultSet result, Class clazz) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        T item;
        ArrayList<T> list = new ArrayList<>();
        HashMap<String, RecordedField> fields = Recorder.getModels().get(this.getClass().getName()).getFields();
        while(result.next()){
            item = (T) clazz.getDeclaredConstructor().newInstance();
            for(var field : fields.keySet()){
                Class cls;
                if(isParentsField(field, clazz)){
                    cls = clazz.getSuperclass();
                }else{
                    cls = clazz;
                }
                cls.getDeclaredField(field)
                        .set(item, switch(fields.get(field).getType()){
                    case INT -> result.getInt(field);
                    case LONG -> result.getLong(field);
                    default -> result.getString(field);
                });
            }
            list.add(item);
        }
        return list;
    }
    
    private PreparedStatement getSelectQuaryForItemWithId(int id) throws SQLException{
        String tableName = getThisModelTableName();
        String sql = "SELECT * FROM " + tableName + " ";
        sql += "WHERE id = ?";
        var stmt = Connector.getConnection().prepareStatement(sql);
        stmt.setLong(1, id);
        return stmt;
    }
    private PreparedStatement getSelectQuaryForAllItems() throws SQLException{
        String tableName = Recorder.getModels().get(this.getClass().getName()).getName();
        String sql = "SELECT * FROM " + tableName;
        var stmt = Connector.getConnection().prepareStatement(sql);
        return stmt;
    }
    private PreparedStatement getInsertQuaryForOneItem(Model item) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        String sql = Build.insert(Recorder.getRecordedClass(item.getClass()));
        var stmt = Connector.getConnection().prepareStatement(sql);
        fillParameters(stmt, item);
        return stmt;
    }
    private void fillParameters(PreparedStatement stmt, Model item) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        int i = 1;
        for(String fname : Recorder.getRecordedClass(item.getClass()).getFields().keySet()){
            Object value = item.getClass().getField(fname).get(item);
            switch(Recorder.getRecordedClass(item.getClass()).getFields().get(fname).getType()){
                case BOOLEAN -> {
                    stmt.setBoolean(i, (boolean) value);
                }
                case BYTE -> {
                    stmt.setByte(i, (byte) value);
                }
                case DOUBLE -> {
                    stmt.setDouble(i, (double) value);
                }
                case FLOAT -> {
                    stmt.setFloat(i, (float) value);
                }
                case INT -> {
                    stmt.setInt(i, (int) value);
                }
                case LONG -> {
                    stmt.setLong(i, (long) value);
                }
                case SHORT -> {
                    stmt.setShort(i, (short) value);
                }
                case STRING -> {
                    stmt.setString(i, (String) value);
                }
            }
            i++;
        }
    }
    
    private String getThisModelTableName(){
        System.out.println(Recorder.getModels());
        return Recorder.getRecordedClass(this.getClass()).getName();
    }
    
    private boolean isParentsField(String fieldName, Class clazz){
        Field[] fieldsInParent = clazz.getSuperclass().getDeclaredFields();
        for (Field f : fieldsInParent) {
            if (f.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
