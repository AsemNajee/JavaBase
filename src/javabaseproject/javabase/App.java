package javabaseproject.javabase;

import java.sql.SQLException;
import javabaseproject.Main;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class App {
    public static void start() throws SQLException, Exception {
        Container.bootstrap();
        Main.main(null);
    }
}
