package javabaseproject.javabase.core.database.querybuilders.query;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.Recorder;

/**
 * joins in queries
 *
 * @author AsemNajee
 * @version 1.0
 */
public class Join<J extends Model<J>> {
    private final Class<J> model;
    private final Condition condition;
    private final JoinType type;

    public Class<J> getModel() {
        return model;
    }

    public Condition getCondition() {
        return condition;
    }

    public JoinType getType() {
        return type;
    }

    private Join(Class<J> model, Condition condition, JoinType type){
        this.model = model;
        this.condition = condition;
        this.type = type;
    }

    public static <J extends Model<J>> Join<J> inner(Class<J> model, Condition condition){
        return new Join<>(model, condition, JoinType.INNER);
    }
    public static <J extends Model<J>> Join<J> outer(Class<J> model, Condition condition){
        return new Join<>(model, condition, JoinType.OUTER);
    }
    public static <J extends Model<J>> Join<J> left(Class<J> model, Condition condition){
        return new Join<>(model, condition, JoinType.LEFT);
    }
    public static <J extends Model<J>> Join<J> right(Class<J> model, Condition condition){
        return new Join<>(model, condition, JoinType.RIGHT);
    }

    @Override
    public String toString() {
        return type + " JOIN " + Recorder.getRecordedClass(model).getName() + " ON " + condition;
    }

    public enum JoinType{
        INNER, OUTER, LEFT, RIGHT
    }
}
