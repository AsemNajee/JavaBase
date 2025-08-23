package javabaseproject.javabase.core.database.statements;

import javabaseproject.javabase.core.database.querybuilders.query.Param;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.Types;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ParameterFiller {
    private ParameterFiller(){}

    public static <M extends Model<M>> void fill(PreparedStatement stmt, Model<M> item, String ...varArgs) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        int i = 1;
        String[] params;
        if(varArgs == null || varArgs.length == 0){
            params = (String[]) Recorder.getRecordedClass(item.getClass()).getFields().keySet().toArray(new String[0]);
        }else{
            params = Arrays.stream(varArgs).filter(field -> Recorder.getRecordedClass(item.getClass()).getFields().containsKey(field)).toList().toArray(new String[0]);
        }
        if(params.length != stmt.getParameterMetaData().getParameterCount()){
            Command.println(Arrays.toString(params));
            for (int j = 0; j < stmt.getParameterMetaData().getParameterCount(); j++) {
                Command.println(stmt.getParameterMetaData().getParameterClassName(i));
            }
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
    }

    public static void fill(PreparedStatement stmt, List<Param> params) throws SQLException {
        int i = 1;
        for (var param : params) {
            bindParam(stmt, param.type, param.value, i++);
        }
    }

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
