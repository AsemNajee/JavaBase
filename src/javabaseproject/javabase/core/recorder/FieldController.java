package javabaseproject.javabase.core.recorder;

import javabaseproject.javabase.core.database.models.Model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this control by the fields in the class/model
 * it let us use the value of the private and protected fields
 */
public class FieldController {
    public static Object get(Field field, Model<? extends Model<?>> instance) throws IllegalAccessException {
        if(instance.isHidden(field)){
            return null;
        }
        field.setAccessible(true);
        Object value = field.get(instance);
        field.setAccessible(false);
        return value;
    }

    public static void set(Field field, RecordedClass.RecordedField rfield, ResultSet result, Model<? extends Model<?>> instance) throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, switch(rfield.getType())
            {
                case INT -> result.getInt(field.getName());
                case LONG -> result.getLong(field.getName());
                case STRING -> result.getString(field.getName());
                case BOOLEAN -> result.getBoolean(field.getName());
                case BYTE -> result.getByte(field.getName());
                case SHORT -> result.getShort(field.getName());
                case DOUBLE -> result.getDouble(field.getName());
                case FLOAT -> result.getFloat(field.getName());
            }
        );
        field.setAccessible(false);
    }
}
