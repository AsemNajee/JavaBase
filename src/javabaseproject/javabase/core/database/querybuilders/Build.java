package javabaseproject.javabase.core.database.querybuilders;

import javabaseproject.ENV;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.RecordedClass;

/**
 * build queries depend on the {@code ENV.DRIVER}
 * support only mysql, and you could add more by your self :)
 *
 * @author AsemNajee
 * @version 1.0
 */
public class Build {

    /**
     * get query of create new table
     *
     * @param rclass the recorded model to get the metadata to create the table
     * @return sql query for creating table
     */
    public static String create(RecordedClass<?> rclass) {
        return switch (ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.createTableQuery(rclass);
            case SQLITE -> SQLITEBuilder.createTableQuery(rclass, false);
            case ORACLE -> null;
        };
    }

    public static String createForeignKeys(RecordedClass<? extends Model<?>> rclass){
        return switch (ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.addingForeignKeys(rclass);
            case SQLITE -> SQLITEBuilder.createTableQuery(rclass, true);
            case ORACLE -> null;
        };
    }

    public static String dropTable(RecordedClass<?> rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.dropTable(rclass);
            case SQLITE -> SQLITEBuilder.dropTable(rclass);
            case ORACLE -> null;
        };
    }
}
