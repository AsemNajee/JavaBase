package javabaseproject.javabase.core.database.querybuilders;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.RecordedClass.RecordedField;
import javabaseproject.javabase.framework.commandline.Command;

/**
 * ready sql queries to insert all instance and delete and update
 *
 * @author AsemNajee
 * @version 1.0
 */
public class MYSQLBuilder {
    public static <M extends Model<M>> String createTableQuery(RecordedClass<M> rclass){
        String tableName = rclass.getName();
        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName.toUpperCase() + " (\n");
        for(String k : rclass.getFields().keySet()){
            RecordedClass.RecordedField field = rclass.getFields().get(k);
            sql.append(field.getName()).append(" ").append(field.getType().getType()).append(filterConstraints(field)).append(", \n");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 3));
        sql.append("\n);");
        return sql.toString();
    }
    
    public static <M extends Model<M>> String insertQuery(RecordedClass<M> rclass){
        StringBuilder fields = new StringBuilder();
        for(String f : rclass.getFields().keySet()){
            fields.append(f).append(", ");
        }
        fields = new StringBuilder(fields.substring(0, fields.length() - 2));
        String sql = """
                     INSERT INTO {{table}}
                        ({{fields}})
                     VALUES
                        ({{values}});
                     """.replace("{{table}}", rclass.getName())
                        .replace("{{fields}}", fields.toString())
                        .replace("{{values}}", fields.toString().replaceAll("[A-Za-z]+", "?"));
        return sql;
    }
    
    public static <M extends Model<M>> String selectItemQuery(RecordedClass<M> rclass){
        return """
                SELECT * FROM {{table}}
                WHERE id = ?
                """.replace("{{table}}", rclass.getName());
    }

    public static <M extends Model<M>> String selectAllQuery(RecordedClass<M> rclass){
        return """
                SELECT * FROM {{table}}
                """.replace("{{table}}", rclass.getName());
    }

    public static <M extends Model<M>> String deleteItemQuery(RecordedClass<M> rclass) {
        return """
                DELETE FROM {{table}}
                WHERE id = ?
                """.replace("{{table}}", rclass.getName());
    }

    public static <M extends Model<M>> String dropTable(RecordedClass<M> rclass){
        return """  
                DROP TABLE {{table}};
                """.replace("{{table}}", rclass.getName());
    }

    /**
     * filter all constraints and get them as string as query sql
     *
     * @param f the field to get its constraints
     * @return constraints as string
     */
    private static String filterConstraints(RecordedField f){
        StringBuilder subSql = new StringBuilder();
        for(var constraint : f.getConstraints()){
            subSql.append(" ").append(constraint);
        }
        subSql.append(" ").append(f.references());
        return subSql.toString();
    }
}
