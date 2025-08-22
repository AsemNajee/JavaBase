package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.interfaces.Jsonable;
import javabaseproject.javabase.framework.commandline.output.Console;

public class Command {
    public static void println(Object text){
        System.out.println(Console.style(text.toString()));
    }
    public static void print(Model<? extends Model<?>> model){
        println(model.toJson());
    }
    public static void print(Jsonable models){
        println(models.toJson());
    }
    public static void print(Object text){
        System.out.print(Console.style(text.toString()));
    }
    public static void printf(Object text, Object ...varArgs){
        System.out.printf(Console.style(text.toString()) + "\n", varArgs);
    }
}
