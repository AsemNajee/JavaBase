package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.core.interfaces.Jsonable;
import javabaseproject.javabase.framework.commandline.output.Colors;
import javabaseproject.javabase.framework.commandline.output.Console;
import javabaseproject.javabase.framework.commandline.output.OutputTemplate;

import java.util.Arrays;

/**
 * output to the command line console styled and colored
 * <ul>
 *     <li>r{red text}</li>
 *     <li>r[background red text]</li>
 * </ul>
 * <br/>
 * that is the role, so you can apply it with the following<br/>
 * <ur>
 *     <li>r : red</li>
 *     <li>b : blue</li>
 *     <li>g : green</li>
 *     <li>y : yellow</li>
 *     <li>p : purple</li>
 *     <li>w : white</li>
 *     <li>k : black</li>
 * </ur>
 *
 * @apiNote you can escape braces with escape character
 * like "r{Hello, {Asem\\}}" this will print =>  Hello, {Asem} <br/>
 * you must escape }] and "
 *
 * @author AsemNajee
 * @version 1.0
 */
public class Command {

    /**
     * print an object.toString() styled
     *
     * @param text the object value
     */
    public static void println(Object text){
        if(text == null)
            Command.println("null");
        else
            System.out.println(Console.style(text.toString()));
    }

    /**
     * printing the model.toJson or collection of models
     *
     * @param model the value of model to print
     */
    public static void println(Jsonable model){
        if(model == null)
            println("null");
        else
            printJson(model.toJson());
    }

    /**
     * printing the model.toJson or collection of models
     *
     * @param models the value of model to print
     */
    public static void print(Jsonable models){
        if(models == null)
            print("null");
        else
            printJson(models.toJson());
    }

    /**
     * print an object.toString() styled
     *
     * @param text the object value
     */
    public static void print(Object text){
        if(text == null)
            Command.print("null");
        else
            System.out.print(Console.style(text.toString()));
    }

    /**
     * print text or object styled and can be formatted see {@link System#out#printf(Object, Object...)}
     *
     * @param text the object value
     */
    public static void printf(Object text, Object ...varArgs){
        if(text == null)
            println("null");
        else{
            varArgs = Arrays.stream(varArgs).map(item -> Console.style(item.toString())).toArray();
            System.out.printf(Console.style(text.toString()) + "\n", varArgs);
        }
    }

    public static void printTemplate(String content){
        printTemplate(content, "g");
    }
    public static void printTemplate(String content, String color){
        printTemplate("", content, color);
    }
    public static void printTemplate(String title, String content, String color){
        var template = OutputTemplate.format(title, content);
         printf(color + "{" + template.getOutput() + "}", template.getParams().toArray());
    }

    /**
     * printing json with colors in the console
     *
     * @param json text of json to print
     */
    public static void printJson(String json) {
        if (json == null){
            print("null");
            return;
        }
        char colorOfStrings     = 'g';
        char colorOfCarlyBraces = 'p';
        char colorOfNumbers     = 'y';
        char colorOfSquerBraces = 'b';
        Command.println(json
                .replaceAll("(?s)\\{(.*?)}",                    colorOfCarlyBraces  + "{{}$1p{\\\\}}")
                .replaceAll("(?s)\\[(.*?)]",                    colorOfSquerBraces  + "{[}$1b{]}")
                .replaceAll("(?s)\"(?<s>.*?)(?<!\\\\)\"",       colorOfStrings      + "{\"${s}\"}")
                .replaceAll("(?s)(\\d)",                        colorOfNumbers      + "{$1}")
                .replaceAll("(?<pre>:( )*)(?<keyword>true|false|null)( )*","${pre}" + colorOfSquerBraces  + "{${keyword}}")
        );
    }
}
