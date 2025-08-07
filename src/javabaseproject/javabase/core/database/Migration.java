package javabaseproject.javabase.core.database;

import java.sql.SQLException;
import javabaseproject.javabase.MyModels;
import javabaseproject.javabase.config.ENV;
import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.quarybuilders.Build;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Migration {
    /**
     * publish all models as tables in database
     * @throws SQLException 
     */
    public static void migrateAll() throws Exception{
        var regModls = MyModels.getRegisteredModels();
        for(RecordedClass r : regModls.values()){
            migrate(r);
        }
    }

    public static boolean migrate(RecordedClass cls) throws SQLException{
        String quary = Build.create(cls);
        return Connector.getConnection().createStatement().execute(quary);
    }
    
    public static boolean initDatabase() throws SQLException{
        String sql = "CREATE DATABASE " + ENV.DATABASE_NAME;
        return DB.execute(sql);
    }
}
