package javabaseproject.javabase.core.database.querybuilders;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.*;
import javabaseproject.javabase.core.recorder.RecordedClass.RecordedField;
import javabaseproject.javabase.framework.commandline.Command;

import java.util.stream.Collectors;

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

    public static <M extends Model<M>> String addingForeignKeys(RecordedClass<M> rclass){
        var fieldsWithForeignKeys = rclass.getFields().values().stream().filter(item -> item.getConstraints().contains(Constraints.FOREIGN_KEY)).collect(Collectors.toList());
        if(fieldsWithForeignKeys.isEmpty()){
            return "";
        }
        StringBuilder sql = new StringBuilder("ALTER TABLE ");
        sql.append(rclass.getName());
        for (var field : fieldsWithForeignKeys) {
            References ref = field.getReferences();
            sql.append("\n").append("ADD CONSTRAINT FOREIGN KEY (").append(field.getName()).append(") ").append(ref).append(",");
        }
        sql.deleteCharAt(sql.length()-1);
        return sql.toString();
    }

    public static <M extends Model<M>> String dropTable(RecordedClass<M> rclass){
        return """  
                DROP TABLE {{table}};
                """.replace("{{table}}", rclass.getName());
    }

    /**
     * filter all constraints and get them as string as query sql<br/>
     * FOREIGN_KEY not add because problems of not created tables
     *
     * @param f the field to get its constraints
     * @return constraints as string
     */
    private static String filterConstraints(RecordedField f){
        StringBuilder subSql = new StringBuilder();
        for(var constraint : f.getConstraints()){
            if(Constraints.FOREIGN_KEY.equals(constraint)){
                continue;
            }
            subSql.append(" ").append(constraint);
            if(Constraints.DEFAULT.equals(constraint)){
                subSql.append(" '").append(f.defaultValue()).append("'");
            }
        }
        return subSql.toString();
    }
}
