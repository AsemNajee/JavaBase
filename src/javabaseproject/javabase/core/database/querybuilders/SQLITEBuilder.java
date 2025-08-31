package javabaseproject.javabase.core.database.querybuilders;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.Constraints;
import javabaseproject.javabase.core.recorder.RecordedClass;

public class SQLITEBuilder {
    public static String createTableQuery(RecordedClass<?> rclass, boolean withForeignKeys){
        String tableName = rclass.getName();
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableName.toUpperCase()).append(" (\n");
        for(String k : rclass.getFields().keySet()){
            RecordedClass.RecordedField field = rclass.getFields().get(k);
            sql.append(field.getName()).append(" ").append(field.getType().getType()).append(filterConstraints(field, withForeignKeys)).append(", \n");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 3));
        sql.append("\n);");
        return sql.toString();
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
    private static String filterConstraints(RecordedClass.RecordedField f, boolean withForeignKeys){
        StringBuilder subSql = new StringBuilder();
        for(var constraint : f.getConstraints()){
            subSql.append(" ");
            if(Constraints.FOREIGN_KEY.equals(constraint)){
                if(!withForeignKeys){
                    continue;
                }
                subSql.append(f.getReferences());
            }else{
                subSql.append(constraint);
            }
            if(Constraints.DEFAULT.equals(constraint)){
                subSql.append(" '").append(f.defaultValue()).append("'");
            }
        }
        return subSql.toString();
    }
}
