package javabaseproject.javabase.core.recorder;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.Command;

/**
 * 
 * @author AsemNajee
 */
public enum Constraints {
    UNIQUE, PRIMARY_KEY("PRIMARY KEY"), NOT_NULL("NOT NULL"), FOREIGN_KEY, AUTO_INCREMENT, DEFAULT;

    private final String sqlConstraint;
    private Class<? extends Model<?>> foreignModel;
    Constraints(String cons){
        sqlConstraint = cons;
    }
    Constraints(){
        sqlConstraint = this.name();
    }

//    public static Constraints FOREIGN_KEY(Class<? extends Model<?>> foreignModel) {
//        var cons = Constraints.FOREIGN_KEY;
//        RecordedClass<?> fRClass = Recorder.getRecordedClass(foreignModel);
//        cons.sqlConstraint = "REFERENCES " + fRClass.getName() + "(" + fRClass.getPrimaryKey().getName() + ")";
//        cons.foreignModel = foreignModel;
//        Command.println(cons);
//        return cons;
//    }

    @Override
    public String toString(){
        return sqlConstraint.replaceAll("_(?>!INCREMENT)", " "); // not replace the underscore of the auto increment constraint
    }
}