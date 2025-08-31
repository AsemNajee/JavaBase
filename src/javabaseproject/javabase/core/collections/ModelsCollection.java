package javabaseproject.javabase.core.collections;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.interfaces.Jsonable;

import java.util.ArrayList;

/**
 * this collection is array list, but you can call method called toJson
 * to print all models in it with json structure
 * @param <M>
 */
public class ModelsCollection<M extends Jsonable> extends ArrayList<M> implements Jsonable {
    public ModelsCollection(){
        super();
    }
    public ModelsCollection(int length){
        super(length);
    }

    /**
     * chang data from a model object to json string it will return
     * all data in the collection as a json string with only one tab
     * in the beginning of each line to format the json
     *
     * @return data as json string
     */
    public String toJson(){
        return toJson(0);
    }

    /**
     * chang data from a model object to json string it will return
     * all data in the collection as a json string with {@code level} tabs
     * in the beginning of each line to format the json
     *
     * @param level count tabs before each line to format the output
     * @return data as json string
     */
    @Override
    public String toJson(int level){
        if(this.isEmpty())
            return "[]";
        String prefix = "\t".repeat(level);
        StringBuilder totalJson = new StringBuilder(prefix).append("[\n");
        for (M model : this) {
            totalJson.append(model.toJson(1 + level)).append(",\n");
        }
        totalJson.delete(totalJson.lastIndexOf(","), totalJson.length());
        totalJson.append("\n").append(prefix).append("]");
        return totalJson.toString();
    }
}
