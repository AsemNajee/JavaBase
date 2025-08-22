package javabaseproject.javabase.core.database.querybuilders.query;

import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.recorder.Types;

public class Param{
    public final Types type;
    public final String value;

    protected Param(String key, String value, String table){
        RecordedClass.RecordedField field = Recorder.getRecordedClass(table).getField(key);
        this.type = field.getType();
        this.value = value;
    }
}