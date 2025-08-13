package javabaseproject.javabase.core.database.statements;

import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.core.database.models.Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * create prepared statements and fills there's parameters
 * by {@code ParameterFiller} or by hand
 */
public class StatementBuilder {

    public static PreparedStatement getSelectQueryForItemWithKey(Class<? extends Model<?>> clazz, Object key) throws SQLException, NoSuchFieldException, IllegalAccessException {
        String sql = Build.select(Recorder.getRecordedClass(clazz));
        var stmt = Connector.getConnection().prepareStatement(sql);
        ParameterFiller.bindParam(
                stmt,
                Recorder.getRecordedClass(clazz).getPrimaryKey().getType(),
                key, 1);
        return stmt;
    }
    public static PreparedStatement getSelectQueryForAllItems(Class clazz) throws SQLException{
        String sql = Build.selectAll(Recorder.getRecordedClass(clazz));
        return Connector.getConnection().prepareStatement(sql);
    }
    public static PreparedStatement getInsertQueryForOneItem(Model<? extends Model<?>> item) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        String sql = Build.insert(Recorder.getRecordedClass(item.getClass()));
        var stmt = Connector.getConnection().prepareStatement(sql);
        ParameterFiller.fill(stmt, item);
        return stmt;
    }
    public static PreparedStatement getDeleteQueryForOneItem(Model<? extends Model<?>> item) throws SQLException, NoSuchFieldException, IllegalAccessException {
        String sql = Build.delete(Recorder.getRecordedClass(item.getClass()));
        var stmt = Connector.getConnection().prepareStatement(sql);
        ParameterFiller.fill(stmt, item, Recorder.getRecordedClass(item.getClass()).getPrimaryKey().getName());
        return stmt;
    }
}
