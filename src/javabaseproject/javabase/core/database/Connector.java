 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package javabaseproject.javabase.core.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javabaseproject.javabase.config.ENV;
import static javabaseproject.javabase.core.database.Drivers.MYSQL;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Connector {
    private static java.sql.Connection c;
    public static java.sql.Connection getConnection() {
        try {
            if(c != null)
                return c;
            
            c = switch(ENV.DRIVER){
                case MYSQL -> DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb", "root", "");
                default -> DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb", "root", "");
            };
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, "Fails to connect with database", ex);
            return null;
        }
    }
}
