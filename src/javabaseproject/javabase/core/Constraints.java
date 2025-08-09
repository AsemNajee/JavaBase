package javabaseproject.javabase.core;

/**
 * 
 * @author AsemNajee
 */
public enum Constraints {
    UNIQUE, PRIMARY_KEY("PRIMARY KEY"), NOT_NULL("NOT NULL"), /*FOREIGN_KEY("REFERENCES")*/;

    private String sqlConstraint;
    Constraints(String cons){
        sqlConstraint = cons;
    }
    Constraints(){
        sqlConstraint = this.name();
    }

    @Override
    public String toString(){
        return sqlConstraint.replace("_", " ");
    }
}