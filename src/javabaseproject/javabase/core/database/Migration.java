package javabaseproject.javabase.core.database;

import java.sql.SQLException;
import javabaseproject.javabase.MyModels;
import javabaseproject.javabase.config.ENV;
import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.database.querybuilders.Build;
import javabaseproject.javabase.output.Colors;
import javabaseproject.javabase.output.Style;

/**
 * 
 * @author AsemNajee
 */
public class Migration {
    /**
     * publish all models as tables in database
     * @throws SQLException 
     */
    public static void migrateAll() {
        var regModls = MyModels.getRegisteredModels();
        String tables = "";
        for(RecordedClass rclass : regModls.values()){
            if(migrate(rclass)){
                tables += "\n" + Style.textColor("DONE", Colors.GREEN) + ": " + rclass.getName();
            }else{
                tables += "\n" + Style.textColor("FAIL", Colors.RED) + ": " + rclass.getName();
            }
        }
        System.out.println(Style.textColor("tables migrated: ", Colors.BLUE) + tables);
    }

    public static boolean migrate(RecordedClass cls) {
        String query = Build.create(cls);
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
