package javabaseproject.javabase.core.recorder;

/**
 * Record reference of the foreign Key constraint
 *
 * @author AsemNajee
 * @version 1.0
 */
public class References {
    private final RecordedClass<?> model;
    private final RecordedClass.RecordedField field;

    public References(RecordedClass<?> model){
        this(model, model.getPrimaryKey());
    }
    public References(RecordedClass<?> model, RecordedClass.RecordedField field){
        this.model = model;
        this.field = field;
    }

    public RecordedClass<?> getModel() {
        return model;
    }

    public RecordedClass.RecordedField getField() {
        return field;
    }

    @Override
    public String toString() {
        return "REFERENCES " + model.getName() + "(" + field.getName() + ")";
    }
}
