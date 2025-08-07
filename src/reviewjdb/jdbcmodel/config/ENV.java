 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package reviewjdb.jdbcmodel.config;

 import reviewjdb.jdbcmodel.core.database.Drivers;

 public class ENV {
    public static Drivers driver;
    
    
    
    static {
        driver = Drivers.MYSQL;
    }
}
