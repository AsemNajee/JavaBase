package javabaseproject.javabase.core.database.statements;

import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.core.database.models.Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * create prepared statements and fills there's parameters
 * by {@code ParameterFiller} or by hand
 *
 * @author AsemNajee
 * @version 1.0
 */
public class StatementBuilder {
    public static <M extends Model<M>> PreparedStatement getUpdateQueryForOneItem(Model<M> item) throws NoSuchFieldException, IllegalAccessException, SQLException {
        String sql = Build.update(item);
        var stmt = Connector.getConnection().prepareStatement(sql);
        int last = ParameterFiller.fill(stmt, item);
//       bind the condition param
        ParameterFiller.bindParam(stmt, Recorder.getRecordedClass(item.getClass()).getPrimaryKey().getType(), item.getKey(), last);
        return stmt;
    }
}
