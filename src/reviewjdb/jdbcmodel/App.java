package reviewjdb.jdbcmodel;

import java.sql.SQLException;
import reviewjdb.Main;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class App {
    public static void main(String[] args) throws SQLException, Exception {
        Container.bootstrap();
        Main.main(args);
    }
}
