package javabaseproject.javabase.core.database.querybuilders.query;

import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.framework.commandline.Command;

import java.util.Arrays;

/**
 * adding conditions to queries
 *
 * @author AsemNajee
 * @version 1.0
 */
public class Condition{
    protected String left;
    protected String right;
    protected String operation;
    private final ConditionType type;
    /**
     * values of the placeholders in condition,
     * can be more than one if the condition is (IN) condition
     */
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

    public static Condition where(String left, ModelsCollection<?> right){
        return whereIn(left, right.toArray());
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
        Command.println(Arrays.toString(values));
        return switch (type){
            case NORMAL -> left + " " + operation + " ?";
            case COLUMN, IN -> left + " " + operation + " " + right;
            case LIKE -> null;
        };
    }

    private enum ConditionType{
        NORMAL, COLUMN, IN, LIKE
    }

    /**
     * implode the in condition to params instead of values
     *
     * @param ar all values from in condition
     * @return result of in condition braces
     */
    private static String implode(Object[] ar){
        return Arrays.toString(ar)
                .replace("[", "(")
                .replace("]", ")")
                .replaceAll("[^,() ]+", "?");
    }
}

