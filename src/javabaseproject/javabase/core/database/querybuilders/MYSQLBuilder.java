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
        Command.println(sql);
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
    
    public static <M extends Model<M>> String insertQuery(Model<? extends M> model) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder fields = new StringBuilder();
        RecordedClass<? extends M> rclass = (RecordedClass<? extends M>) Recorder.getRecordedClass(model.getClass());
        for(String f : rclass.getFields().keySet()){
//            continue if the field is null and has a default value
//            because the database can apply the default value
            if(rclass.getField(f).defaultValue() != null && FieldController.get(f, model) == null){
                continue;
            }
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

    public static  <M extends Model<M>> String update(Model<M> model) throws NoSuchFieldException, IllegalAccessException {
        RecordedClass<? extends M> rclass = (RecordedClass<? extends M>) Recorder.getRecordedClass(model.getClass());
        String sql = """
                UPDATE {{table}} SET {{cols}}
                WHERE {{key}} = ?
                """;
        String tableName = rclass.getName();
        sql = sql.replace("{{table}}", tableName);
        StringBuilder cols = new StringBuilder();
        for (var field : rclass.getFields().keySet()) {
            if(rclass.getField(field).defaultValue() != null && FieldController.get(field, model) == null){
                continue;
            }
            cols.append("\n").append(field).append(" = ?,");
        }
        cols.deleteCharAt(cols.length()-1);
        sql = sql.replace("{{cols}}", cols);
        sql = sql.replace("{{key}}", rclass.getPrimaryKey().getName());
        Command.println(sql);
        return sql;
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
