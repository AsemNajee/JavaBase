package javabaseproject.javabase.core.database.statements;

import javabaseproject.javabase.core.database.querybuilders.query.Param;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.Types;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.Arrays;
import java.util.List;

/**
 * filler of the prepared statements queries
 *
 * @author AsemNajee
 * @version 1.0
 */
public class ParameterFiller {
    private ParameterFiller(){}

    /**
     * fill the statement by params from the model instance
     *
     * @param stmt the statement to fill its params
     * @param item the instance to get the data from its fields
     * @param varArgs specify some fields names to get them values
     */
    public static <M extends Model<M>> int fill(PreparedStatement stmt, Model<M> item, String ...varArgs) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        int i = 1;
        String[] params;
        RecordedClass<? extends Model<?>> rclass = Recorder.getRecordedClass(item.getClass());
        if(varArgs == null || varArgs.length == 0){
            params = rclass.getFields().keySet().toArray(new String[0]);
        }else{
            params = Arrays.stream(varArgs).filter(field -> Recorder.getRecordedClass(item.getClass()).getFields().containsKey(field)).toList().toArray(new String[0]);
        }
        for(String fname : params){
            RecordedClass.RecordedField rField = rclass.getField(fname);
            Field field;
            if(rField.isParentField()){
                field = item.getClass().getSuperclass().getDeclaredField(fname);
            }else{
                field = item.getClass().getDeclaredField(fname);
            }
            if(rclass.getField(fname).defaultValue() != null && FieldController.get(fname, item) == null){
                continue;
            }
            Object value = FieldController.get(field, item);
            bindParam(
                    stmt,
                    Recorder.getRecordedClass(item.getClass()).getField(fname).getType(),
                    value,
                    i
            );
            i++;
        }
        return i;
    }

    /**
     * fill statement from values from the list
     *
     * @param stmt the statement to fill its params
     * @param params values to bind them in placeholders
     */
    public static void fill(PreparedStatement stmt, List<Param> params) throws SQLException {
        int i = 1;
        for (var param : params) {
            bindParam(stmt, param.type, param.value, i++);
        }
    }

    /**
     * bind a param value to statement
     * @param stmt statement to bind the param
     * @param type type fo the value and the field in database
     * @param value the value to bind it
     * @param i the order of the param
     */
    public static void bindParam(PreparedStatement stmt, Types type, Object value, int i) throws SQLException {
        switch(type){
            case BOOLEAN -> {
                stmt.setBoolean(i, Boolean.parseBoolean(String.valueOf(value)));
            }
            case BYTE -> {
                stmt.setByte(i, Byte.parseByte(String.valueOf(value)));
            }
            case DOUBLE -> {
                stmt.setDouble(i, Double.parseDouble(String.valueOf(value)));
            }
            case FLOAT -> {
                stmt.setFloat(i, Float.parseFloat(String.valueOf(value)));
            }
            case INT -> {
                stmt.setInt(i, Integer.parseInt(String.valueOf(value)));
            }
            case LONG -> {
                stmt.setLong(i, Long.parseLong(String.valueOf(value)));
            }
            case SHORT -> {
                stmt.setShort(i, Short.parseShort(String.valueOf(value)));
            }
            case STRING -> {
                stmt.setString(i, String.valueOf(value));
            }
        }
    }
}
