package javabaseproject.javabase;

import java.sql.SQLException;
import java.util.HashMap;
import javabaseproject.javabase.core.RecordedClass;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.quarybuilders.Build;
import javabaseproject.javabase.core.framework.MigrationsModel;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Container {
    public static void bootstrap() throws Exception{
        var regModls = MyModels.getRegisteredModels();
//        var migratedModels = new MigrationsModel().getAll(MigrationsModel.class);
        migrate(regModls);
    }
    
    /**
     * publish all models as tables in database
     * @param recs
     * @throws SQLException 
     */
    private static void migrate(HashMap<String, RecordedClass> recs) throws SQLException{
//        for(RecordedClass r : recs.values()){
//            migrate(r);
//        }
//        for(var v : recs.keySet()){
//            System.out.println(v + "=> " + recs.get(v));
//        }
    }
    private static boolean migrate(RecordedClass cls) throws SQLException{
        String quary = Build.create(cls);
        return Connector.getConnection().createStatement().execute(quary);
    }
}
