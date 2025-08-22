package javabaseproject.javabase.core.interfaces;

public interface Jsonable {
    /**
     * change the model object to json string
     * @return result of json string
     */
    String toJson();
    String toJson(int level);
}
