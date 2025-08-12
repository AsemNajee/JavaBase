package javabaseproject.javabase.core.database;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;

import java.util.ArrayList;


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
        Command.println(count + " items was seeded", Colors.GREEN);
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
