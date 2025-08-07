/**
 *   >> Al-Reecha .~
 *   << BY : Asem Najee >>
 */
package reviewjdb.jdbcmodel.core.database.quarybuilders;

import reviewjdb.jdbcmodel.config.ENV;
import reviewjdb.jdbcmodel.core.RecordedClass;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class BuildCreateTableQuary {

    public static String build(RecordedClass clazz) {
        String quary = switch (ENV.driver) {
            case MYSQL ->
                MYSQLBuilder.createTableQuary(clazz);
            default ->
                MYSQLBuilder.createTableQuary(clazz);
        };
        return quary;
    }
}
