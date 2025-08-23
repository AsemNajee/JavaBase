package javabaseproject.javabase.core.database.querybuilders.query;

import java.util.Arrays;

public class Condition{
    protected String left;
    protected String right;
    protected String operation;
    private final ConditionType type;
    protected Object[] values;
    protected int parametersCount;

    private Condition(String left, String operation, String right){
        this(left, operation, right, ConditionType.NORMAL, 1, right);
    }
    private Condition(String left, String operation, String right, ConditionType type, int parametersCount, Object... values){
        this.left = left;
        this.operation = operation;
        this.right = right;
        this.type = type;
        this.parametersCount = parametersCount;
        this.values = values;
    }

    public static Condition where(String left, String operation, String right){
        return new Condition(left, operation, right);
    }
    public static Condition where(String left, String right){
        return where(left, "=", right);
    }

    public static Condition whereIn(String left, Object[] right){
        var con = new Condition(left, " IN ", implode(right), ConditionType.IN, right.length);
        con.values = right;
        return con;
    }

    public static Condition on(String left, String right){
        return on(left, "=", right);
    }
    public static Condition on(String left, String operation, String right){
        return new Condition(left, operation, right, ConditionType.COLUMN, 0);
    }

    @Override
    public String toString() {
        return switch (type){
            case NORMAL -> left + " " + operation + " ?";
            case COLUMN, IN -> left + " " + operation + " " + right;
            case LIKE -> null;
        };
    }

    private enum ConditionType{
        NORMAL, COLUMN, IN, LIKE
    }

    private static String implode(Object[] ar){
        return Arrays.toString(ar)
                .replace("[", "(")
                .replace("]", ")")
                .replaceAll("[^,() ]+", "?");
    }
}

