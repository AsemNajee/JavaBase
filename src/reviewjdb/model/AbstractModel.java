 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package reviewjdb.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import reviewjdb.jdbcmodel.core.Recorder;
import static reviewjdb.jdbcmodel.core.RecordedClass.RecordedField;
import reviewjdb.jdbcmodel.core.database.Connector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public abstract class AbstractModel<T> {
    
    public abstract T find(int id) throws Exception;
    public abstract ArrayList<T> getAll() throws Exception;
    
    protected T find(int id, Class clazz) throws SQLException, IllegalArgumentException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        T item;
        var stmt = this.getSelectQuaryForItemWithId(id);
        var result = stmt.executeQuery();
        item = fetch(result, clazz).get(id);
        return item;
    }
    
    protected ArrayList<T> getAll(Class clazz) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        T item = (T)new Object();
        var stmt = this.getSelectQuaryForAllItems();
        var result = stmt.executeQuery();
        return fetch(result, clazz);
    }
    
    protected ArrayList<T> fetch(ResultSet result, Class clazz) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        T item;
        ArrayList<T> list = new ArrayList<>();
        HashMap<String, RecordedField> fields = Recorder.getModels().get(this.getClass().getName()).getFields();
        int i = 0;
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
    
    private String getThisModelTableName(){
        return Recorder.getModels().get(this.getClass().getName()).getName();
    }
    
    private boolean isParentsField(String fieldName, Class clazz){
        Field[] fieldsInParent = clazz.getSuperclass().getDeclaredFields();
        for (int i = 0; i < fieldsInParent.length; i++) {
            if(fieldsInParent[i].getName().equals(fieldName)){
                return true;
            }
        }
        return false;
    }
}
