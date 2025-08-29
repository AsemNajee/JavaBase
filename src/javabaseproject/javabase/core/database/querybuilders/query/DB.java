package javabaseproject.javabase.core.database.querybuilders.query;


import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.io.Fetcher;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.statements.ParameterFiller;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.Recorder;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * building a query string and execute it by easy way
 *
 * @param <T> the type of the model returning when executing the query
 */
public class DB<T extends Model<T>> {
    /**
     * store the table name to do the query
     */
    private final String table;

    /**
     * store all columns to retrieve from the db, default is (*)
     */
    private List<String> cols;

    /**
     * collection of column names to order by
     */
    private List<String> orderBy;

    /**
     * collection of column names to group by
     */
    private List<String> groupBy;

    /**
     * collection of conditions to apply them in the query
     */
    private final List<Condition> conditions;

    /**
     * data of params in the preparedStatement for security
     */
    private final List<Param> params;

    /**
     * collection of joins to apply them in the query
     */
    private final List<Join<?>> joins;
    private final Map<String, Param> colsToUpdate;

    /**
     * maximum row retrieved from the database
     */
    private int limit;
    private QueryType type;

    public DB(String table){
        this.table = table;
        this.cols = new LinkedList<>();
        this.orderBy = new LinkedList<>();
        this.groupBy = new LinkedList<>();
        this.conditions = new LinkedList<>();
        this.params = new LinkedList<>();
        this.joins = new LinkedList<>();
        this.colsToUpdate = new HashMap<>();
    }

    /**
     * execute the query and get one item retrieved from the result
     *
     * @param cols columns to get from the database, the default is (*)
     * @return new instance from the database contains the result of the query
     */
    public T get(String... cols) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        type = QueryType.SELECT;
        if(cols != null && cols.length != 0){
            this.cols = toList(cols);
        }
        var result = executeQuery();
        if(result.next()){
            return Fetcher.fetch(Recorder.<T>getRecordedClass(table).getClazz(), result);
        }
        return null;
    }

    /**
     * execute the query and get all items retrieved from the result
     *
     * @param cols columns to get from the database, the default is (*)
     * @return collection of new instances from the database contains the result of the query
     */
    public ModelsCollection<T> all(String ...cols) throws Exception {
        type = QueryType.SELECT;
        if(cols != null && cols.length != 0){
            this.cols = toList(cols);
        }
        return Fetcher.fetchAll(
                Recorder.<T>getRecordedClass(table).getClazz(),
                executeQuery()
        );
    }

    private static <M extends Model<M>> ResultSet insertModel(M model) throws NoSuchFieldException, IllegalAccessException, SQLException {
        DB<M> db = DB.from(model.getClass());
        var rclass = Recorder.getRecordedClass(model.getClass());
        db.type = QueryType.INSERT;
        for (var field : rclass.getFields().keySet()) {
//            continue if the field is null and has a default value
//            because the database can apply the default value
            if(rclass.getField(field).defaultValue() != null && FieldController.get(field, model) == null){
                continue;
            }
            db.colsToUpdate.put(field, new Param(field, String.valueOf(FieldController.get(field, model)), db.table));
        }
        var stmt = db.prepare();
        stmt.executeUpdate();
        return stmt.getGeneratedKeys();
    }

    /**
     * insert model to the database
     *
     * @param model instance of model to insert into database
     * @return the model which inserted to the database
     */
    public static <M extends Model<M>> M insert(M model) throws SQLException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        var generatedKey = insertModel(model);
        if(generatedKey.next()){
            return (M) DB.from(model.getClass()).where(generatedKey).get();
        }else{
            return (M) DB.from(model.getClass()).where(model.getKey()).get();
        }
    }

    /**
     * insert collection of models and return there new values
     *
     * @param models collection of models
     * @return models from the database
     */
    @SafeVarargs
    public static <M extends Model<M>> ModelsCollection<M> insertAll(M... models) throws Exception {
        if(models == null || models.length == 0){
            return null;
        }
        Object[] keys = new Object[models.length];
        int i = 0;
        for (M model : models) {
            var generatedKeys = insertModel(model);
            if(generatedKeys.next()){
                keys[i++] = generatedKeys.getObject(1);
            }else{
                keys[i++] = model.getKey();
            }
        }
        String primaryKey = Recorder.getRecordedClass(models[0].getClass()).getPrimaryKey().getName();
        return DB.from((Class<M>) models[0].getClass())
                .whereIn(primaryKey, keys)
                .all();
    }

    /**
     * update the model in the database with new data,
     * if there are no conditions automatically will add condition
     * {@code where(model.getKey())} to the query to save your data
     *
     * @param model fresh data of the model to update
     * @return rows effected
     */
    public int update(T model) throws NoSuchFieldException, IllegalAccessException, SQLException {
        type = QueryType.UPDATE;
        if(conditions.isEmpty()){
            where(model.getKey());
        }
        for (var field : Recorder.getRecordedClass(model.getClass()).getFields().keySet()) {
            set(field, FieldController.get(field, model));
        }
        return executeUpdate();
    }

    public DB<T> set(String column, Object value){
        type = QueryType.UPDATE;
        this.colsToUpdate.put(column, new Param(column, String.valueOf(value), table));
        return this;
    }

    /**
     * delete items from the database, don't forget to use where condition
     *
     * @return all deleted items
     */
    public ModelsCollection<T> delete() throws Exception {
        type = QueryType.DELETE;
        var db = new DB<T>(table);
        db.conditions.addAll(conditions);
        var deletedRows = db.all();
        execute();
        return deletedRows;
    }

    public PreparedStatement prepare() throws SQLException {
        String sql = toQueryString();
        PreparedStatement stmt = Connector.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ParameterFiller.fill(stmt, params);
        return stmt;
    }

    /**
     * execute the query built and return result set
     *
     * @return the result from the query
     */
    public ResultSet executeQuery() throws SQLException {
        var stmt = prepare();
        return stmt.executeQuery();
    }
    public int executeUpdate() throws SQLException {
        var stmt = prepare();
        return stmt.executeUpdate();
    }
    public boolean execute() throws SQLException {
        var stmt = prepare();
        return stmt.execute();
    }

    /**
     * change the built query to sql string query to execute in the database
     *
     * @return sql string query
     */
    public String toQueryString(){
        StringBuilder output = new StringBuilder();
        switch (type){
            case SELECT -> {
                output.append("SELECT ");
                if(cols.isEmpty()){
                    output.append("*");
                }else{
                    output.append(implode(cols, ", "));
                }
                output.append("\n");
                output.append("FROM ").append(table).append(" ");
                output.append("\n");
                if(!joins.isEmpty()){
                    output.append(implodeJoin(joins, " AND "));
                    output.append("\n");
                }
                if(!conditions.isEmpty()){
                    output.append(" WHERE ").append(implodeCondition(conditions, " AND "));
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
            }
            case DELETE -> {
                output.append("DELETE FROM ").append(table).append(" ");
                if(!conditions.isEmpty()){
                    output.append("WHERE ").append(implodeCondition(conditions, " AND "));
                    output.append("\n");
                }
                if(limit > 0){
                    output.append("LIMIT ").append(limit);
                }
            }
            case INSERT -> {
                output.append("INSERT INTO ").append(table).append(" (");
                output.append(implode(colsToUpdate.keySet().stream().toList(), ", "));
                output.append(") ");
                output.append("VALUES (").append(implode(colsToUpdate.keySet().stream().map(item -> "?").toList(), ", "));
                params.addAll(colsToUpdate.values());
                output.append(") ");
            }
            case UPDATE -> {
                output.append("UPDATE ").append(table).append(" SET ");
                for (var col : colsToUpdate.keySet()) {
                    output.append(col).append(" = ?, ");
                    params.add(colsToUpdate.get(col));
                }
                output.deleteCharAt(output.length()-2); // delete last comma
                if(!conditions.isEmpty()){
                    output.append("WHERE ").append(implodeCondition(conditions, " AND "));
                    output.append("\n");
                }else{
                    throw new RuntimeException("Must add condition to update query");
                }
                if(!orderBy.isEmpty()){
                    output.append("ORDER BY ").append(implode(orderBy, ", "));
                    output.append("\n");
                }
                if(limit > 0){
                    output.append("LIMIT ").append(limit);
                }
            }
        }
        return output.toString();
    }

    /**
     * add columns to order the result by
     *
     * @param cols columns to order the result
     * @return this
     */
    public DB<T> orderBy(String... cols){
        if(cols != null && cols.length != 0){
            orderBy = toList(cols);
        }
        return this;
    }

    /**
     * adding columns to group the result by
     *
     * @param cols columns to group the result
     * @return this
     */
    public DB<T> groupBy(String... cols){
        if(cols != null && cols.length != 0){
            groupBy = toList(cols);
        }
        return this;
    }

    /**
     * adding condition to the query
     *
     * @param left the column name
     * @param operation the operation between the column and the value
     * @param right the value to apply the operation between it and the column
     * @return this
     */
    public DB<T> where(String left, String operation, Object right){
        conditions.add(Condition.where(left, operation, String.valueOf(right)));
        return this;
    }

    public DB<T> where(Object key){
        where(Recorder.getRecordedClass(table).getPrimaryKey().getName(), key);
        return this;
    }

    /**
     * adding condition to the query
     *
     * @param left the column name
     * @param right the value to apply the operation between it and the column
     * @apiNote the default operation is equals (=) -> left = right,
     *
     * @apiNote the link between all conditions is (AND)
     * @return this
     */
    public DB<T> where(String left, Object right){
        conditions.add(Condition.where(left, String.valueOf(right)));
        return this;
    }

    /**
     * adding condition with in sql keyword
     * @param left column name
     * @param right values of the in condition
     * @return this
     */
    public DB<T> whereIn(String left, Object... right){
        conditions.add(Condition.whereIn(left, right));
        return this;
    }

    /**
     * set the limit of the retrieved values from the database
     * @param limit the number of rows
     * @return this
     */
    public DB<T> limit(int limit){
        this.limit = limit <= 0 ? 10 : limit;
        return this;
    }

    /**
     * adding join to the query, type of join is outer join
     *
     * @param model the model to join with it
     * @param condition the on condition to make a relation between models
     * @return this
     */
    public <J extends Model<J>> DB<T> outerJoin(Class<J> model, Condition condition){
        joins.add(Join.outer(model, condition));
        return this;
    }

    /**
     * adding join to the query, type of join is inner join
     *
     * @param model the model to join with it
     * @param condition the on condition to make a relation between models
     * @return this
     */
    public <J extends Model<J>> DB<T> innerJoin(Class<J> model, Condition condition){
        joins.add(Join.inner(model, condition));
        return this;
    }

    /**
     * adding join to the query, type of join is left join
     *
     * @param model the model to join with it
     * @param condition the on condition to make a relation between models
     * @return this
     */
    public <J extends Model<J>> DB<T> leftJoin(Class<J> model, Condition condition){
        joins.add(Join.left(model, condition));
        return this;
    }

    /**
     * adding join to the query, type of join is right join
     *
     * @param model the model to join with it
     * @param condition the on condition to make a relation between models
     * @return this
     */
    public <J extends Model<J>> DB<T> rightJoin(Class<J> model, Condition condition){
        joins.add(Join.right(model, condition));
        return this;
    }

    /**
     * create new query builder instead of the constructor
     *
     * @param clazz the model to create query to query from its table
     * @return new instance from the query builder
     */
    public static <M extends Model<M>> DB<M> from(Class<M> clazz){
        return new DB<>(Recorder.getRecordedClass(clazz).getName());
    }

    /**
     * helper method
     */
    private static List<String> toList(String ...array){
        return Arrays.stream(array).toList();
    }

    /**
     * read all list like columns and return the value as string separated by a separator
     *
     * @param list list to implode its elements
     * @param separator the separator between the elements
     * @return imploded list as string
     */
    private String implode(List<String> list, String separator){
        StringBuilder output = new StringBuilder();
        for (var item : list) {
            output.append(item).append(separator);
        }
        output.delete(output.lastIndexOf(separator), output.length());
        return output.toString();
    }

    /**
     * read all conditions in the query and return the value
     * as string separated by a separator
     *
     * @param conditions list to implode its elements
     * @param separator the separator between the elements
     * @return imploded list as string
     */
    private String implodeCondition(List<Condition> conditions, String separator){
        StringBuilder output = new StringBuilder();
        for (var condition : conditions) {
            addParams(condition, table);
            output.append(condition).append(separator);
        }
        output.delete(output.lastIndexOf(separator), output.length());
        return output.toString();
    }

    /**
     * read all joins in the query and return the value
     * as string separated by a separator
     *
     * @param joins list to implode its elements
     * @param separator the separator between the elements
     * @return imploded list as string
     */
    private String implodeJoin(List<Join<?>> joins, String separator){
        StringBuilder output = new StringBuilder();
        for (var join : joins) {
            addParams(join.getCondition(), Recorder.getRecordedClass(join.getModel()).getName());
            output.append(join).append(separator);
        }
        output.delete(output.lastIndexOf(separator), output.length());
        return output.toString();
    }

    /**
     * save params in the temporary list to bind them when execute the query
     *
     * @param condition the condition to save its parameters values
     *                  after placing the by placeholder (?)
     * @param table the parent table of the field that has a value we saved it <br/>
     *              this useful when getting the type of the field when binding the param
     */
    private void addParams(Condition condition, String table){
        for (int i = 0; i < condition.parametersCount; i++) {
            params.add(new Param(condition.left, String.valueOf(condition.values[i]), table));
        }
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
