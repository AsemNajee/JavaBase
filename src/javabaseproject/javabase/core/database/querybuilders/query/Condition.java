package javabaseproject.javabase.core.database.querybuilders.query;

public class Condition{
    protected String left;
    protected String right;
    protected String operation;

    private Condition(String left, String operation, String right){
        this.left = left;
        this.operation = operation;
        this.right = right;
    }

    protected static Condition where(String left, String operation, String right){
        return new Condition(left, operation, right);
    }
    protected static Condition where(String left, String right){
        return new Condition(left, "=", right);
    }

    @Override
    public String toString() {
        return left + " " + operation + " ?";
    }
}

