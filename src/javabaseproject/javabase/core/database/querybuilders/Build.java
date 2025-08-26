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
            default -> MYSQLBuilder.createTableQuery(rclass);
        };
    }

    /**
     * get query to insert new row in the database table with all fields form the model
     *
     * @param rclass model to insert the data into its table
     * @return sql query of inserting with placeholder
     */
    public static String insert(RecordedClass<?> rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.insertQuery(rclass);
            default -> MYSQLBuilder.insertQuery(rclass);
        };
    }

    /**
     * get query to delete a row from the database using primary key
     *
     * @param rclass recorded model to get metadata as primary key
     * @return sql query to delete an item with primary key with placeholder
     */
    public static String delete(RecordedClass<?> rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.deleteItemQuery(rclass);
            default -> MYSQLBuilder.deleteItemQuery(rclass);
        };
    }

    /**
     * get query to select one item from the database
     *
     * @param rclass .
     * @return .
     */
    public static String select(RecordedClass<?> rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.selectItemQuery(rclass);
            default -> MYSQLBuilder.selectItemQuery(rclass);
        };
    }

    public static String selectAll(RecordedClass<?> rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.selectAllQuery(rclass);
            default -> MYSQLBuilder.selectAllQuery(rclass);
        };

    }

    public static String dropTable(RecordedClass<?> rclass){
        return switch(ENV.DRIVER) {
            case MYSQL -> MYSQLBuilder.dropTable(rclass);
            default -> MYSQLBuilder.dropTable(rclass);
        };
    }
}
