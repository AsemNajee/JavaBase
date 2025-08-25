package javabaseproject.javabase.core.database.models;

import javabaseproject.database.models.Book;
import javabaseproject.database.models.BookPerson;
import javabaseproject.database.models.Person;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.querybuilders.query.Condition;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;

import java.util.List;

public class Relations {
    /**
     * get one field from {@code foreignModel} table
     * with foreign key of the {@code model}
     * @param model instance who ownership of the relation (has a primary key)
     * @param foreignModel model class of the model child (has a foreign key)
     * @return new instance from {@code foreignModel} contains a value from db
     */
    public static <T extends Model<T>, F extends Model<F>> F hasOne(T model, Class<F> foreignModel,String foreignKey, String localKey) throws Exception {
        String relationKeyValue = FieldController.get(localKey, model).toString();
        return DB.from(foreignModel)
                .limit(1)
                .where(foreignKey, relationKeyValue)
                .get();
    }
    public static <T extends Model<T>, F extends Model<F>> F hasOne(T model, Class<F> foreignModel, String foreignKey) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(model.getClass());
        String localKey = rclass.getPrimaryKey().getName();
        return hasOne(model, foreignModel, foreignKey, localKey);
    }
    public static <T extends Model<T>, F extends Model<F>> F hasOne(T model, Class<F> foreignModel) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(model.getClass());
        String localKey = rclass.getPrimaryKey().getName();
        String foreignKey = rclass.getName().toLowerCase() + "_" + localKey;
        return hasOne(model, foreignModel, foreignKey, localKey);
    }


    public static <T extends Model<T>, F extends Model<F>> ModelsCollection<F> hasMany(T model, Class<F> foreignModel, String foreignKey, String localKey) throws Exception {
        String relationKeyValue = FieldController.get(localKey, model).toString();
        return DB.from(foreignModel).where(foreignKey, relationKeyValue).all();
    }
    public static <T extends Model<T>, F extends Model<F>> List<F> hasMany(T model, Class<F> foreignModel, String foreignKey) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(model.getClass());
        String localKey = rclass.getPrimaryKey().getName();
        return hasMany(model, foreignModel, foreignKey, localKey);
    }
    public static <T extends Model<T>, F extends Model<F>> ModelsCollection<F> hasMany(T model, Class<F> foreignModel) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(model.getClass());
        String localKey = rclass.getPrimaryKey().getName();
        String foreignKey = rclass.getName().toLowerCase() + "_" + localKey;
        return hasMany(model, foreignModel, foreignKey, localKey);
    }


    public static <T extends Model<T>, F extends Model<F>> F belongsTo(T model, Class<F> parentModel,String foreignKey, String localKey) throws Exception {
        String relationKeyValue = FieldController.get(foreignKey, model).toString();
        return Model.of(parentModel).query().where(localKey, relationKeyValue).get();
    }
    public static <T extends Model<T>, F extends Model<F>> F belongsTo(T model, Class<F> parentModel, String foreignKey) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(parentModel);
        String localKey = rclass.getPrimaryKey().getName();
        return belongsTo(model, parentModel, foreignKey, localKey);
    }
    public static <T extends Model<T>, F extends Model<F>> F belongsTo(T model, Class<F> parentModel) throws Exception {
        RecordedClass<?> rclass = Recorder.getRecordedClass(parentModel);
        String localKey = rclass.getPrimaryKey().getName();
        String foreignKey = rclass.getName().toLowerCase() + "_" + localKey;
        return belongsTo(model, parentModel, foreignKey, localKey);
    }


    public static <R extends Model<R>, M extends Model<M>, P extends Pivot<?, ?>> ModelsCollection<R> belongsToMany(Class<P> pivot, Class<R> returnedItem, M model) throws Exception {
        String keyFromPivotToModel = Recorder.getRecordedClass(pivot).getForeignKeyWith((Class<? extends Model<?>>) model.getClass());
        var relationKeys = Model.of(BookPerson.class).query().where(keyFromPivotToModel, model.getKey()).all();
        var keys = relationKeys.stream().map(Model::getKey);
        return DB.from(returnedItem)
                .whereIn(
                        Recorder.getRecordedClass(returnedItem).getPrimaryKey().getName(),
                        keys.toArray())
                .all();
    }

    /**
     * id
     * user_id
     * book_id
     */
//    public static <T extends Model<T>, F extends Model<F>, P extends Pivot<T, F>> ModelsCollection<F> belongsToMany(T model, Class<F> parentModel, Class<P> pivot, String foreignKey, String localKey) throws Exception {
//        String relationKeyValue = FieldController.get(foreignKey, model).toString();
////        SELECT * FROM pivot WHERE model_id = ?
//        String sql = DB.fromPivot(pivot).where(foreignKey, relationKeyValue).innerJoin(parentModel, Condition.on(foreignKey, localKey)).toQueryString();
//        return null;
//    }
//    public static <T extends Model<T>, F extends Model<F>> List<F> belongsToMany(T model, Class<F> parentModel, String foreignKey) throws Exception {
//        RecordedClass<F> rclass = Recorder.getRecordedClass(parentModel);
//        String localKey = rclass.getPrimaryKey().getName();
//        return belongsToMany(model, parentModel, foreignKey, localKey);
//    }
//    public static <T extends Model<T>, F extends Model<F>> ModelsCollection<F> belongsToMany(T model, Class<F> parentModel) throws Exception {
//        RecordedClass<F> rclass = Recorder.getRecordedClass(parentModel);
//        String localKey = rclass.getPrimaryKey().getName();
//        String foreignKey = rclass.getName().toLowerCase() + "_" + localKey;
//        return belongsToMany(model, parentModel, foreignKey, localKey);
//    }


    public static String getForeignKeyName(RecordedClass<?> localModel, RecordedClass<?> foreignModel){
        String foreignModelName = foreignModel.getName().substring(0, 1).toLowerCase() + foreignModel.getName().substring(1, foreignModel.getName().length());
        return foreignModelName + "_" + localModel.getPrimaryKey().getName();
    }
}
