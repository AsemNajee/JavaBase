 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package reviewjdb.jdbcmodel.core.database.quarybuilders;

import reviewjdb.jdbcmodel.core.RecordedClass;
import reviewjdb.jdbcmodel.core.RecordedClass.RecordedField;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class MYSQLBuilder {
    public static String createTableQuary(RecordedClass clazz){
        String tableName = clazz.getName();
        String sql = "CREATE TABLE " + tableName.toUpperCase() + " (\n";
        String[] columnsSql = new String[clazz.getFields().size()];
        for(String k : clazz.getFields().keySet()){
            RecordedClass.RecordedField field = clazz.getFields().get(k);
            sql += field.getName() + " " + 
                   field.getType().getType() +
                   filterConstraints(field) + ", \n";
        }
        sql = sql.substring(0, sql.length() -3);
        sql += "\n);";
        return sql;
    }
    
    public static String filterConstraints(RecordedField f){
        
        return " ";
    }
}
