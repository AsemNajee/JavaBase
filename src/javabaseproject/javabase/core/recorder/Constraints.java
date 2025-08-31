package javabaseproject.javabase.core.recorder;

/**
 * 
 * @author AsemNajee
 */
public enum Constraints {
    UNIQUE, PRIMARY_KEY("PRIMARY KEY"), NOT_NULL("NOT NULL"), FOREIGN_KEY, AUTO_INCREMENT, DEFAULT;

    private final String sqlConstraint;
    Constraints(String cons){
        sqlConstraint = cons;
    }
    Constraints(){
        sqlConstraint = this.name();
    }

    @Override
    public String toString(){
        return sqlConstraint.replaceAll("_(?>!INCREMENT)", " "); // not replace the underscore of the auto increment constraint
    }
}