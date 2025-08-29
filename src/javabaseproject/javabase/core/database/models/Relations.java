package javabaseproject.javabase.core.database.models;

import javabaseproject.database.models.BookPerson;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;

import java.util.List;

public class Relations {
    /**
     * get one field from {@code foreignModel} table
     * with foreign key of the {@code model}
     *
     * @param model instance who ownership of the relation (has a primary key)
     * @param foreignModel model class of the model child (has a foreign key)
     * @param foreignKey the foreign key name in the model {@code foreignModel}
     *                   to search by the column value
     * @param localKey the primary key name in the model to search by its value
     * @return new instance from {@code foreignModel} contains a value from db
     */
    public static <T extends Model<T>, F extends Model<F>> F hasOne(T model, Class<F> foreignModel,String foreignKey, String localKey) throws Exception {
        String relationKeyValue = FieldController.get(localKey, model).toString();
        return DB.from(foreignModel)
                .limit(1)
                .where(foreignKey, relationKeyValue)
                .get();
    }

    /**
     * get one field from {@code foreignModel} table
     * with foreign key of the {@code model} </br>
     * this help if the foreign key is not named as the convention
     *
     * @param model instance who ownership of the relation (has a primary key)
     * @param foreignModel model class of the model child (has a foreign key)
     * @param foreignKey the foreign key name in the model {@code foreignModel}
     *                   to search by the column value
     * @return new instance from {@code foreignModel} contains a value from db
     */
    public static <T extends Model<T>, F extends Model<F>> F hasOne(T model, Class<F> foreignModel, String foreignKey) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(model.getClass());
        String localKey = rclass.getPrimaryKey().getName();
        return hasOne(model, foreignModel, foreignKey, localKey);
    }

    /**
     * get one field from {@code foreignModel} table
     * with foreign key of the {@code model} <br/>
     *
     * <br/><b>Naming convention of the foreign key</b>
     * you can just use the default convention of the foreign key name
     * by naming your foreign key like ParentModel then KeyName of the parent model
     * <br/><b>Example:</b><br/>
     * relation from the model [Book] with key (id) to the model [User] with key (id)
     * each user has a book and only one book, so the foreign key in the books model
     * will be named like [userId] so that contains the name of the parent and the name of
     * key in the parent, don't forget <i><u>java is case sensitive</u></i>
     *
     * @param model instance who ownership of the relation (has a primary key)
     * @param foreignModel model class of the model child (has a foreign key)
     * @return new instance from {@code foreignModel} contains a value from db
     */
    public static <T extends Model<T>, F extends Model<F>> F hasOne(T model, Class<F> foreignModel) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(model.getClass());
        String localKey = rclass.getPrimaryKey().getName();
        String foreignKey = rclass.getName().toLowerCase() + "_" + localKey;
        return hasOne(model, foreignModel, foreignKey, localKey);
    }

    /**
     * get all fields from {@code foreignModel} table that related
     * with foreign key of the {@param model} <br/>
     *
     * @param model instance who ownership of the relation (has a primary key)
     * @param foreignModel model class of the model child (has a foreign key)
     * @param foreignKey the foreign key name in the model {@code foreignModel}
     *                   to search by the column value
     * @param localKey the primary key name in the model to search by its value
     * @return collection of new instances from {@code foreignModel} containing a value from db
     */
    public static <T extends Model<T>, F extends Model<F>> ModelsCollection<F> hasMany(T model, Class<F> foreignModel, String foreignKey, String localKey) throws Exception {
        String relationKeyValue = FieldController.get(localKey, model).toString();
        return DB.from(foreignModel).where(foreignKey, relationKeyValue).all();
    }

    /**
     * get all fields from {@code foreignModel} table that related
     * with foreign key of the {@param model} <br/>
     * this help if the foreign key is not named as the convention
     *
     * @param model instance who ownership of the relation (has a primary key)
     * @param foreignModel model class of the model child (has a foreign key)
     * @param foreignKey the foreign key name in the model {@code foreignModel}
     *                   to search by the column value
     * @return collection of new instances from {@code foreignModel} containing a value from db
     */
    public static <T extends Model<T>, F extends Model<F>> List<F> hasMany(T model, Class<F> foreignModel, String foreignKey) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(model.getClass());
        String localKey = rclass.getPrimaryKey().getName();
        return hasMany(model, foreignModel, foreignKey, localKey);
    }

    /**
     * get all fields from {@code foreignModel} table that related
     * with foreign key of the {@param model} <br/>
     *
     * <br/><b>Naming convention of the foreign key</b>
     * you can just use the default convention of the foreign key name
     * by naming your foreign key like ParentModel then KeyName of the parent model
     * <br/><b>Example:</b><br/>
     * relation from the model [Book] with key (id) to the model [User] with key (id)
     * each user has a book and only one book, so the foreign key in the books model
     * will be named like [userId] so that contains the name of the parent and the name of
     * key in the parent, don't forget <i><u>java is case sensitive</u></i>
     *
     * @param model instance who ownership of the relation (has a primary key)
     * @param foreignModel model class of the model child (has a foreign key)
     * @return collection of new instances from {@code foreignModel} containing a value from db
     */
    public static <T extends Model<T>, F extends Model<F>> ModelsCollection<F> hasMany(T model, Class<F> foreignModel) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(model.getClass());
        String localKey = rclass.getPrimaryKey().getName();
        String foreignKey = rclass.getName().toLowerCase() + "_" + localKey;
        return hasMany(model, foreignModel, foreignKey, localKey);
    }


    /**
     * get the parent model from the child in <b>hasMany</b> relationship
     *
     * @param model instance that you want to get it's linked models
     * @param parentModel class of the parent model to return an instance of it
     * @param foreignKey foreign key name in the child model to get its value for search
     * @param localKey the primary key name of the parent model
     * @return new instance contains the data retrieved from the database
     */
    public static <T extends Model<T>, F extends Model<F>> F belongsTo(T model, Class<F> parentModel,String foreignKey, String localKey) throws Exception {
        String relationKeyValue = FieldController.get(foreignKey, model).toString();
        return Model.of(parentModel).query().where(localKey, relationKeyValue).get();
    }

    /**
     * get the parent model from the child in <b>hasMany</b> relationship
     *
     * @param model instance that you want to get it's linked models
     * @param parentModel class of the parent model to return an instance of it
     * @param foreignKey foreign key name in the child model to get its value for search
     * @return new instance contains the data retrieved from the database
     */
    public static <T extends Model<T>, F extends Model<F>> F belongsTo(T model, Class<F> parentModel, String foreignKey) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(parentModel);
//        need to fix the problem of linking key not the primary key
        String localKey = rclass.getPrimaryKey().getName();
        return belongsTo(model, parentModel, foreignKey, localKey);
    }

    /**
     * get the parent model from the child in <b>hasMany</b> relationship
     *
     * @param model instance that you want to get it's linked models
     * @param parentModel class of the parent model to return an instance of it
     *
     * @apiNote for naming convention see {@link #hasMany(Model, Class)}
     * @return new instance contains the data retrieved from the database
     */
    public static <T extends Model<T>, F extends Model<F>> F belongsTo(T model, Class<F> parentModel) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(parentModel);
        String localKey = rclass.getPrimaryKey().getName();
        String foreignKey = rclass.getName().toLowerCase() + "_" + localKey;
        return belongsTo(model, parentModel, foreignKey, localKey);
    }

    /**
     * get all models from the database liked with another model by many-to-many
     * relationship using pivot table
     *
     * @param pivot the pivot model of the pivot table, it's also a model
     * @param returnedItem class type of the items will be returned from the relation
     * @param model the ownership of the relationship
     *
     * @return collection of new instances with type of {@code returnedItem}
     */
    public static <R extends Model<R>, M extends Model<M>, P extends Model<P>> ModelsCollection<R> belongsToMany(Class<P> pivot, Class<R> returnedItem, M model) throws Exception {
        String keyFromPivotToModel = Recorder.getRecordedClass(pivot).getForeignKeyWith((Class<? extends Model<?>>) model.getClass());
        String localKey = Recorder.getRecordedClass(pivot).getField(keyFromPivotToModel).getReferences().getField().getName();
        var relationKeys = Model.of(pivot).query().where(keyFromPivotToModel, FieldController.get(localKey, model)).all();
        var keys = relationKeys.stream().map(Model::getKey);
        return DB.from(returnedItem)
                .whereIn(
                        Recorder.getRecordedClass(returnedItem).getPrimaryKey().getName(),
                        keys.toArray())
                .all();
    }
}
