package javabaseproject.javabase.core.interfaces;

/**
 * object can change to json data
 *
 * @author AsemNajee
 * @version 1.0
 */
public interface Jsonable {

    /**
     * chang data from a model object to json string it will return
     * all data in the collection as a json string with only one tab
     * in the beginning of each line to format the json
     *
     * @return data as json string
     */
    String toJson();

    /**
     * chang data from a model object to json string it will return
     * all data in the collection as a json string with {@code level} tabs
     * in the beginning of each line to format the json
     *
     * @param level count tabs before each line to format the output
     * @return data as json string
     */
    String toJson(int level);
}
