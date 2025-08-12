package javabaseproject.javabase.framework.commandline.output;

public class Style {
    public static String textColor(String text, Colors clr){
        return clr.textColor() + text + clr.reset();
    }
    
    public static String bgColor(String text, Colors clr){
        return clr.bgColor() + text + clr.reset();
    }

    public static String style(String text, Colors bgColor, Colors fgColor){
        return textColor(bgColor(text, bgColor), fgColor);
    }
}
