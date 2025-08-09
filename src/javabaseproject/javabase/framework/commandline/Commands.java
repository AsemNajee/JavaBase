package javabaseproject.javabase.framework.commandline;

import javabaseproject.javabase.output.Colors;
import javabaseproject.javabase.output.Style;

import java.util.regex.Pattern;

public class Commands {
    public static void println(String text){
        System.out.println(text);
    }
    public static void println(String text, Colors textColor){
        System.out.println(Style.textColor(text, textColor));
    }
    public static void println(String text, Colors textColor, Colors bgColor){
        System.out.println(Style.style(text, bgColor, textColor));
    }
    public static void print(String text){
        System.out.print(text);
    }
    public static void print(String text, Colors textColor){
        System.out.print(Style.textColor(text, textColor));
    }
    public static void print(String text, Colors textColor, Colors bgColor){
        System.out.print(Style.style(text, bgColor, textColor));
    }

    public static void printf(String text, String ...vararage){
        System.out.printf(text, vararage);
    }
}
