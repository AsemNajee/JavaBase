/**
 *   >> Al-Reecha .~
 *   << BY : Asem Najee >>
 */
package reviewjdb.jdbcmodel.core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * record information about class to be able to change it to database table
 *
 * @author PC
 */
public class RecordedClass {

    private String name;
    private HashMap<String, RecordedField> fields;

    public RecordedClass(String name) {
        this.name = name;
        fields = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<String, RecordedField> getFields() {
        return fields;
    }

    protected void addField(String key, RecordedField value) {
        fields.put(key, value);
    }

    /**
     * add constraints to field this is information about the field
     *
     * @param field field name as string like its name in the class model
     * @param constraint constraint from Constraints Enum
     * @param more more constraints
     * @return
     */
    public RecordedClass constraint(String field, Constraints constraint, Constraints... more) {
        if (!fields.containsKey(field)) {
//            throw an error
        }
        fields.get(field).constraints.add(constraint);
        if (more != null) {
            for (Constraints c : more) {
                fields.get(field).constraints.add(c);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "RecordedClass{" + "name=" + name + ", fields=" + fields + '}';
    }

    /**
     * store only one field information
     */
    public static class RecordedField {

        protected String name;
        protected Types type;
        protected ArrayList<Constraints> constraints;

        public RecordedField() {
            constraints = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public Types getType() {
            return type;
        }

        @Override
        public String toString() {
            return "RecordedField{" + "name=" + name + ", type=" + type + ", constraints=" + constraints + '}';
        }
    }
}

