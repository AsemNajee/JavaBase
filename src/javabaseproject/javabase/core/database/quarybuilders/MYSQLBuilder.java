package javabaseproject.javabase.core.database.quarybuilders;

import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.RecordedClass.RecordedField;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class MYSQLBuilder {
    public static String createTableQuary(RecordedClass rclass){
        String tableName = rclass.getName();
        String sql = "CREATE TABLE " + tableName.toUpperCase() + " (\n";
        for(String k : rclass.getFields().keySet()){
            RecordedClass.RecordedField field = rclass.getFields().get(k);
            sql += field.getName() + " " + 
                   field.getType().getType() +
                   filterConstraints(field) + ", \n";
        }
        sql = sql.substring(0, sql.length() -3);
        sql += "\n);";
        return sql;
    }
    
    private static String filterConstraints(RecordedField f){
        
        return " ";
    }
    
    public static String insertQuary(RecordedClass rclass){
        String fields = ""; 
        for(String f : rclass.getFields().keySet()){
            fields += f + ", ";
        }
        fields = fields.substring(0, fields.length() -2);
        String sql = """
                     INSERT INTO {{table}} 
                        ({{fields}}) 
                     VALUES 
                        ({{values}});
                     """.replace("{{table}}", rclass.getName())
                        .replace("{{fields}}", fields)
                        .replace("{{values}}", fields.replaceAll("[A-Za-z]+", "?"));
        return sql;
    }
    
    public static String selectItemQuary(RecordedClass rclass){
        return """
                SELECT * FROM {{table}} 
                WHERE id = ?
                """.replace("{{table}}", rclass.getName());
    }
}
