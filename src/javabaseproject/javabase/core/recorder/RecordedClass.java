package javabaseproject.javabase.core.recorder;

import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.Seeder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * record information about class to be able to change it to database table
 *
 * @author PC
 */
public class RecordedClass<T extends Model<T>> {
    private final String name;
    private final HashMap<String, RecordedField> fields;
    private RecordedField primary;
    private Factory<T> factory;
    private Seeder seeder;
    private final Class<T> clazz;

    public RecordedClass(String name, Class<T> clazz) {
        this(name, new HashMap<>(), null, clazz);
    }
    
    public RecordedClass(String name, HashMap<String, RecordedField> fields, RecordedField primary, Class<T> clazz){
        this.name = name;
        this.fields = fields;
        this.primary = primary;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, RecordedField> getFields() {
        return fields;
    }
    
    public RecordedField getField(String name){
        return fields.get(name);
    }
    
    public RecordedField getPrimaryKey(){
        return primary;
    }
    public Class<T> getClazz(){
        return clazz;
    }
    
    protected void setPrimaryKey(RecordedField primary){
        this.primary = primary;
    }

    protected void addField(String key, RecordedField value) {
        fields.put(key, value);
    }

    public Factory<T> getFactory() {
        return factory;
    }

    public void setFactory(Factory<T> factory) {
        this.factory = factory;
    }

    public Seeder getSeeder() {
        return seeder;
    }

    public void setSeeder(Seeder seeder) {
        this.seeder = seeder;
    }

    /**
     * add constraints to field this is information about the field
     *
     * @param field field name as string like its name in the class model
     * @param constraint constraint from Constraints Enum
     * @param more more constraints
     */
    public RecordedClass<T> constraint(String field, Constraints constraint, Constraints... more) {
        if (!fields.containsKey(field)) {
//            throw an error
        }
        fields.get(field).constraints.add(constraint);
        if (more != null) {
            Collections.addAll(fields.get(field).constraints, more);
        }
        return this;
    }

    @Override
    public String toString() {
        return "\nRecordedClass{\n" + "\tname=" + name + ", \n\tclazz=" + clazz + ", \n\tfields=" + fields + "\n}";
    }

    /**
     * store only one field information
     */
    public static class RecordedField {

        private final String name;
        private final Types type;
        private final ArrayList<Constraints> constraints;
        private final boolean parentField;
        private final Field field;
        private Class<? extends Model<?>> references;

        private final boolean hidden;

        public RecordedField(String name, Types type, ArrayList<Constraints> constraints, Field field, boolean parentField, boolean isHidden) {
            this.name = name;
            this.type = type;
            this.constraints = constraints;
            this.parentField = parentField;
            this.hidden = isHidden;
            this.field = field;
        }

        public RecordedField(String name, Types type, ArrayList<Constraints> constraints, Field field){
            this(name, type, constraints, field, false);
        }

        public RecordedField(String name, Types type, ArrayList<Constraints> constraints, Field field, boolean isParent){
            this(name, type, constraints, field, isParent, false);
        }
        
        public RecordedField addConstraint(Constraints cons){
            constraints.add(cons);
            return this;
        }
        public RecordedField references(Class<? extends Model<?>> references){
            this.references = references;
            return this;
        }

        public String getName() {
            return name;
        }

        public Types getType() {
            return type;
        }

        public Field getRealField() {
            return field;
        }
        public String getReferences(){
            if(references == null){
                return "";
            }
            RecordedClass<?> fRClass = Recorder.getRecordedClass(references);
            return "REFERENCES " + fRClass.getName() + "(" + fRClass.getPrimaryKey().getName() + ")";
        }

        /**
         * check if the field is from the parent
         * this help in inheritance
         * @return if the field is from parent
         */
        public boolean isParentField() {
            return parentField;
        }

        public boolean isHidden() {
            return hidden;
        }

        public ArrayList<Constraints> getConstraints(){
            return constraints;
        }

        @Override
        public String toString() {
            return "RecordedField{" + "\n\t\tname=g{" + name + "}, \n\t\ttype=g{" + type + "}, \n\t\tconstraints=g{" + constraints + "}, \n\t\tisParentField=b{" + parentField + "}\n}";
        }
    }
}

