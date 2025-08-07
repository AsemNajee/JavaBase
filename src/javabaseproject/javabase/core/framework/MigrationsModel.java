 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package javabaseproject.javabase.core.framework;

import javabaseproject.model.Model;

/**
 * get data about migrations and tables from database
 * this help no repeating migrations every time
 */
public class MigrationsModel extends Model<MigrationsModel> {
    private String tableName;
    
    public MigrationsModel(){
        super(MigrationsModel.class);
    }
}
