package javabaseproject.javabase.core.collections;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.interfaces.Jsonable;

import java.util.ArrayList;

/**
 * this collection is array list, but you can call method called toJson
 * to print all models in it with json structure
 * @param <M>
 */
public class ModelsCollection<M extends Model<M>> extends ArrayList<M> implements Jsonable {
    public ModelsCollection(){
        super();
    }
    public ModelsCollection(int length){
        super(length);
    }
    public String toJson(){
        return toJson(0);
    }
    @Override
    public String toJson(int level){
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
