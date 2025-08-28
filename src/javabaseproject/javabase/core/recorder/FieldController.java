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
        field.setAccessible(true);
        Object value = field.get(instance);
        field.setAccessible(false);
        return value;
    }

    public static Object get(String field, Model<? extends Model<?>> instance) throws NoSuchFieldException, IllegalAccessException {
        return get(
                instance.getClass().getDeclaredField(field),
                instance
        );
    }

    /**
     * this is used when fetching data from database to set field value
     *
     * @param field
     * @param result
     * @param instance
     */
    public static <M extends Model<M>> void set(Field field, ResultSet result, Model<M> instance) throws SQLException, IllegalAccessException {
        var rfield = Recorder.getRecordedClass(instance.getClass()).getField(field.getName());
        set(
                field,
                getColumnData(rfield.getType(), field.getName(), result),
                instance
        );
    }

    public static Object getColumnData(Types type, String name, ResultSet result) throws SQLException {
        return switch(type)
        {
            case INT -> result.getInt(name);
            case LONG -> result.getLong(name);
            case STRING -> result.getString(name);
            case BOOLEAN -> result.getBoolean(name);
            case BYTE -> result.getByte(name);
            case SHORT -> result.getShort(name);
            case DOUBLE -> result.getDouble(name);
            case FLOAT -> result.getFloat(name);
        };
    }

    public static void set(Field field, Object value, Model<? extends Model<?>> instance) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, value);
        field.setAccessible(false);
    }

    /**
     * this use to set a field value from string
     * it's used in json when convert from json text to instance
     * @param field
     * @param value
     * @param instance
     * @throws IllegalAccessException
     */
    public static void set(Field field, String value, Model<? extends Model<?>> instance) throws IllegalAccessException {
        var rfield = Recorder.getRecordedClass(instance.getClass()).getField(field.getName());
        set(field, switch (rfield.getType()){
            case STRING -> value;
            case INT -> Integer.parseInt(value);
            case FLOAT -> Float.parseFloat(value);
            case LONG -> Long.parseLong(value);
            case SHORT -> Short.parseShort(value);
            case BYTE -> Byte.parseByte(value);
            case DOUBLE -> Double.parseDouble(value);
            case BOOLEAN -> Boolean.parseBoolean(value);
        }, instance);
    }

    public static Types getFieldType(Field field){
        String[] pathToAnnotationType = field.getAnnotatedType().toString().split("\\.");
        return switch(pathToAnnotationType[pathToAnnotationType.length -1]){
            case "boolean"  -> Types.BOOLEAN;
            case "byte"     -> Types.BYTE;
            case "short"    -> Types.SHORT;
            case "int"      -> Types.INT;
            case "long"     -> Types.LONG;
            case "String" -> Types.STRING;
            default -> Types.STRING;
        };
    }
}
