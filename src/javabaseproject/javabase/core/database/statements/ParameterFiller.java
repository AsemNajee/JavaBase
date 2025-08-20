package javabaseproject.javabase.core.database.statements;

import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.Types;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class ParameterFiller {
    private ParameterFiller(){}

    public static PreparedStatement fill(PreparedStatement stmt, Model<? extends Model<?>> item, String ...varArgs) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        int i = 1;
        String[] params;
        if(varArgs == null || varArgs.length == 0){
            params = Recorder.getRecordedClass(item.getClass()).getFields().keySet().toArray(new String[0]);
        }else{
            params = Arrays.stream(varArgs).filter(field -> Recorder.getRecordedClass(item.getClass()).getFields().containsKey(field)).toList().toArray(new String[0]);
        }
        if(params.length != stmt.getParameterMetaData().getParameterCount()){
            throw new SQLException("parameter count is not the same with placeholders cound");
        }
        for(String fname : params){
            RecordedClass.RecordedField rField = Recorder.getRecordedClass(item.getClass()).getField(fname);
            Field field;
            if(rField.isParentField()){
                field = item.getClass().getSuperclass().getDeclaredField(fname);
            }else{
                field = item.getClass().getDeclaredField(fname);
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
        return stmt;
    }

    public static PreparedStatement bindParam(PreparedStatement stmt, Types type, Object value, int i) throws SQLException {
        switch(type){
            case BOOLEAN -> {
                stmt.setBoolean(i, (boolean) value);
            }
            case BYTE -> {
                stmt.setByte(i, (byte) value);
            }
            case DOUBLE -> {
                stmt.setDouble(i, (double) value);
            }
            case FLOAT -> {
                stmt.setFloat(i, (float) value);
            }
            case INT -> {
                stmt.setInt(i, (int) value);
            }
            case LONG -> {
                stmt.setLong(i, (long) value);
            }
            case SHORT -> {
                stmt.setShort(i, (short) value);
            }
            case STRING -> {
                stmt.setString(i, (String) value);
            }
        }
        return stmt;
    }
}
