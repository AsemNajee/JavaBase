package javabaseproject.javabase.core.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import javabaseproject.javabase.MyModels;
import javabaseproject.ENV;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;
import javabaseproject.javabase.framework.commandline.output.Style;

/**
 * 
 * @author AsemNajee
 */
public class Migration {
    /**
     * publish all models as tables in database
     * @throws SQLException 
     */
    public static void migrateAll() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var regModls = MyModels.getRegisteredModels();
        String tables = "";
        for(RecordedClass rclass : regModls.values()){
            if(migrate(rclass)){
                tables += "\ng{DONE} : " + rclass.getName();
            }else{
                tables += "\nr{FAIL}: " + rclass.getName();
            }
        }
        Command.printf("b{tables migrated: }" + tables);
    }

    public static boolean migrate(RecordedClass rclass) {
        String query = Build.create(rclass);
        try(var stmt = Connector.getConnection().createStatement()){
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException se){
            System.out.println(Style.textColor(se.toString(), Colors.YELLOW));
            return false;
        }
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
