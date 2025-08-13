package javabaseproject.javabase.core.database;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;

import java.util.ArrayList;

/**
 * insert fake data to the model, these called factories
 * @param <M> the model class type to make sure the return type of the factory
 *           is the object of the model
 */
public abstract class Factory<M extends Model<M>> {
    public abstract M item();

    public ArrayList<M> make(int count){
        if(count <= 0) {
            count = 1;
        }
        ArrayList<M> models = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            models.add(item());
        }
        Command.printf("g{%d items was seeded}", count);
        return models;
    }

    public ArrayList<M> create(int count) throws Exception {
        ArrayList<M> models = make(count);
        for (int i = 0; i < models.size(); i++) {
            models.get(i).save();
        }
        return models;
    }
}
