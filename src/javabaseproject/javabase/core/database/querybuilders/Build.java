package javabaseproject.javabase.core.database.querybuilders;

import javabaseproject.javabase.config.ENV;
import javabaseproject.javabase.core.RecordedClass;

/**
 * 
 * @author AsemNajee
 */
public class Build {

    public static String create(RecordedClass rclass) {
        return switch (ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.createTableQuery(rclass);
            default -> MYSQLBuilder.createTableQuery(rclass);
        };
    }
    
    public static String insert(RecordedClass rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.insertQuery(rclass);
            default -> MYSQLBuilder.insertQuery(rclass);
        };
    }

    public static String delete(RecordedClass rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.deleteItemQuery(rclass);
            default -> MYSQLBuilder.deleteItemQuery(rclass);
        };
    }

    public static String select(RecordedClass rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.selectItemQuery(rclass);
            default -> MYSQLBuilder.selectItemQuery(rclass);
        };
    }

    public static String selectAll(RecordedClass rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.selectAllQuery(rclass);
            default -> MYSQLBuilder.selectAllQuery(rclass);
        };

    }

    public static String dropTable(RecordedClass rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.dropTable(rclass);
            default -> MYSQLBuilder.dropTable(rclass);
        };
    }
}
