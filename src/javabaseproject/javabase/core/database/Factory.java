package javabaseproject.javabase.core.database;

import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.Command;

/**
 * insert fake data to the model, these called factories
 * @param <M> the model class type to make sure the return type of the factory
 *           is the object of the model
 */
public abstract class Factory<M extends Model<M>> {
    /**
     * make an item with a fake data and return it
     * @return model filled by fake data
     */
    public abstract M item();

    /**
     * making models by using factory of the model and return collection of models </br>
     * this method does not save a data created in the database
     * @param count number of models to create and return in the collection
     * @return a collection of models filled by data from factory
     */
    public ModelsCollection<M> make(int count){
        if(count <= 0) {
            count = 1;
        }
        ModelsCollection<M> models = new ModelsCollection<>();
        for (int i = 0; i < count; i++) {
            models.add(item());
        }
        Command.printf("g{%d items has seeded}", count);
        return models;
    }

    /**
     * creating a collection of models and save them in the database
     * @param count number of models to create
     * @return a collection of models with a fake data
     * @throws Exception throw if save method failed
     */
    public ModelsCollection<M> create(int count) throws Exception {
        ModelsCollection<M> models = make(count);
        for (M model : models) {
            model.save();
        }
        return models;
    }
}
