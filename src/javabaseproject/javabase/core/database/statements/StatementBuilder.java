package javabaseproject.javabase.core.database.statements;

import javabaseproject.javabase.core.Recorder;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.models.Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * create prepared statements and fills there's parameters by {@code ParameterFiller} or by hand
 */
public class StatementBuilder {

    public static PreparedStatement getSelectQueryForItemWithId(Class clazz, int id) throws SQLException, NoSuchFieldException, IllegalAccessException {
        String sql = Build.select(Recorder.getRecordedClass(clazz));
        var stmt = Connector.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt;
    }
    public static PreparedStatement getSelectQueryForAllItems(Class clazz) throws SQLException{
        String sql = Build.selectAll(Recorder.getRecordedClass(clazz));
        var stmt = Connector.getConnection().prepareStatement(sql);
        return stmt;
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
        ParameterFiller.fill(stmt, item, "id");
        return stmt;
    }
}
