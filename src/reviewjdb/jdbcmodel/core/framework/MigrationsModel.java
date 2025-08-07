 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package reviewjdb.jdbcmodel.core.framework;

import reviewjdb.model.Model;
import java.util.ArrayList;

/**
 * get data about migrations and tables from database
 * this help no repeating migrations every time
 */
public class MigrationsModel extends Model<MigrationsModel> {
    private String tableName;
    private ArrayList<MigrationsModel> listOfMigratedTables;
    
    public MigrationsModel(){
        super(MigrationsModel.class);
    }
}
