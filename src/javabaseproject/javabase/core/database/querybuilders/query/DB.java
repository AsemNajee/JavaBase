package javabaseproject.javabase.core.database.querybuilders.query;

// columns & aliases
// table & aliases
// condition
// grouping
// ordering
// join

import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.io.Fetcher;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.statements.ParameterFiller;
import javabaseproject.javabase.core.recorder.Recorder;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DB<T extends Model<T>> {
    private final String table;
    private List<String> cols;
    private List<String> orderBy;
    private List<String> groupBy;
    private List<Condition> conditions;
    private List<Param> params;
    private int limit;

    public DB(String table){
        this.table = table;
        this.cols = new LinkedList<>();
        this.orderBy = new LinkedList<>();
        this.groupBy = new LinkedList<>();
        this.conditions = new LinkedList<>();
        this.params = new LinkedList<>();
    }
    public T get(String... cols) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(cols != null && cols.length != 0){
            this.cols = toList(cols);
        }
        var result = execute();
        if(result.next()){
            return Fetcher.fetch((Class<T>) Recorder.getRecordedClass(table).getClazz(), result);
        }
        return null;
    }

    public ModelsCollection<T> all(String ...cols) throws Exception {
        if(cols != null && cols.length != 0){
            this.cols = toList(cols);
        }
        var result = execute();
        ModelsCollection<T> list = new ModelsCollection<>(limit > 0 ? limit : 10);
        while(result.next()){
            list.add(Fetcher.fetch((Class<T>) Recorder.getRecordedClass(table).getClazz(), result));
        }
        return list;
    }
    private ResultSet execute() throws SQLException {
        String sql = toQueryString();
        PreparedStatement stmt = Connector.getConnection().prepareStatement(sql);
        ParameterFiller.fill(stmt, params);
        return stmt.executeQuery();
    }

    public String toQueryString(){
        StringBuilder output = new StringBuilder("SELECT ");
        if(cols.isEmpty()){
            output.append("*");
        }else{
            output.append(implode(cols, ", "));
        }
        output.append("\n");
        output.append("FROM ").append(table).append(" ");
        output.append("\n");
        if(!conditions.isEmpty()){
            output.append("WHERE ").append(implodeCondition(conditions, " AND "));
            output.append("\n");
        }
        if(!orderBy.isEmpty()){
            output.append("ORDER BY ").append(implode(orderBy, ", "));
            output.append("\n");
        }
        if(!groupBy.isEmpty()){
            output.append("GROUP BY ").append(implode(groupBy, ", "));
            output.append("\n");
        }
        if(limit > 0){
            output.append("LIMIT ").append(limit);
        }
        return output.toString();
    }

    public DB<T> orderBy(String... cols){
        if(cols != null && cols.length != 0){
            orderBy = toList(cols);
        }
        return this;
    }

    public DB<T> groupBy(String... cols){
        if(cols != null && cols.length != 0){
            groupBy = toList(cols);
        }
        return this;
    }

    public DB<T> where(String left, String operation, String right){
        conditions.add(Condition.where(left, operation, right));
        return this;
    }
    public DB<T> where(String left, String right){
        conditions.add(Condition.where(left, right));
        return this;
    }

    public DB<T> limit(int limit){
        this.limit = limit;
        return this;
    }

    public static <M extends Model<M>> DB<M> from(Class<M> clazz){
        return new DB<>(Recorder.getRecordedClass(clazz).getName());
    }

    private static List<String> toList(String ...array){
        return Arrays.stream(array).toList();
    }

    private String implode(List<String> list, String separator){
        StringBuilder output = new StringBuilder();
        for (var item : list) {
            output.append(item).append(separator);
        }
        output.delete(output.lastIndexOf(separator), output.length());
        return output.toString();
    }
    private String implodeCondition(List<Condition> list, String separator){
        StringBuilder output = new StringBuilder();
        for (var item : list) {
            params.add(new Param(item.left, item.right, table));
            output.append(item).append(separator);
        }
        output.delete(output.lastIndexOf(separator), output.length());
        return output.toString();
    }

    @Override
    public String toString() {
        return "DB{" +
                "table='" + table + '\'' +
                ", cols=" + cols +
                ", orderBy=" + orderBy +
                ", groupBy=" + groupBy +
                ", conditions=" + conditions +
                ", params=" + params +
                ", limit=" + limit +
                '}';
    }
}
