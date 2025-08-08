package javabaseproject.javabase.core.database.quarybuilders;

import javabaseproject.javabase.config.ENV;
import javabaseproject.javabase.core.RecordedClass;

/**
 * 
 * @author AsemNajee
 */
public class Build {

    public static String create(RecordedClass rclass) {
        return switch (ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.createTableQuary(rclass);
            default -> MYSQLBuilder.createTableQuary(rclass);
        };
    }
    
    public static String insert(RecordedClass rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.insertQuary(rclass);
            default -> MYSQLBuilder.insertQuary(rclass);
        };
    }

    public static String dropTable(RecordedClass rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.dropTable(rclass);
            default -> MYSQLBuilder.dropTable(rclass);
        };
    }
}
