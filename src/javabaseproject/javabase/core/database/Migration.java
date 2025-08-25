package javabaseproject.javabase.core.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javabaseproject.ENV;
import javabaseproject.javabase.Register;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;
import javabaseproject.javabase.framework.commandline.output.Style;
import javabaseproject.javabase.framework.exceptions.ExceptionHandler;

/**
 * 
 * @author AsemNajee
 */
public class Migration {
    /**
     * publish all models as tables in database
     * @throws SQLException 
     */
    public static void migrateAll() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, SQLException {
        var regModls = Register.getRegisteredModels();
        StringBuilder tables = new StringBuilder();
        for(var rclass : regModls.values()){
            if(migrate(rclass)){
                tables.append("\ng{DONE} : ").append(rclass.getName());
            }else{
                tables.append("\nr{FAIL}: ").append(rclass.getName());
            }
        }
        Command.printf("b{tables migrated: }" + tables);
    }

    public static boolean migrate(RecordedClass<?> rclass) throws SQLException {
        String query = Build.create(rclass);
        try(var stmt = Connector.getConnection().createStatement()){
//            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e){
            ExceptionHandler.print(e);
        }
        return false;
    }
    
    public static boolean initDatabase() throws SQLException{
        String sql = "CREATE DATABASE " + ENV.DATABASE_NAME;
        return DBMS.execute(sql);
    }

    public static boolean dropDatabase() throws SQLException {
        String sql = "DROP DATABASE " + ENV.DATABASE_NAME;
        return DBMS.execute(sql);
    }
}
