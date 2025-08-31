package javabaseproject.javabase.framework.commandline.output;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutputTemplate {
    public static final int LINE_SIZE = 70;
    private String output;
    private List<String> params;

    public static OutputTemplate format(String output){
        return format("", output);
    }
    public static OutputTemplate format(String title, String output){
        return new OutputTemplate(title, output);
    }

    public OutputTemplate(String title, String output){
        params = new ArrayList<>();
        StringBuilder txt = new StringBuilder("+");
        txt.append("-".repeat((LINE_SIZE - title.length())/2));
        txt.append(title);
        txt.append("-".repeat((LINE_SIZE - title.length())/2));
        txt.append("+");
        Pattern p = Pattern.compile("(?<line>.+)");
        Matcher m = p.matcher(output);
        while(m.find()){
            String longLine = m.group("line");
            if(longLine.length() <= LINE_SIZE){
                txt.append("%n").append("|%-").append(LINE_SIZE).append("s|");
                params.add(longLine);
                continue;
            }
            int start = 0;
            int end = getSeparatedFrom(longLine, 0);
            while(start < longLine.length()){
                String sub = longLine.substring(start, end);
                txt.append("%n").append("|%-").append(LINE_SIZE).append("s|");
                params.add(sub.trim());
                start = end;
                end = getSeparatedFrom(longLine, end);
            }
        }
        txt.append("%n").append("+").append("-".repeat(LINE_SIZE)).append("+");
        this.output = txt.toString();
    }

    private int getSeparatedFrom(String txt, int from){
        int endSub = from + LINE_SIZE;
        if(endSub >= txt.length()){
            return txt.length();
        }
        int lastSpace = txt.lastIndexOf(" ", endSub);
        if(lastSpace == -1){
            return endSub + 1;
        }
        return lastSpace;
    }

    public String getOutput(){
        return output;
    }

    public String getMessage(){
        return "g{" + output + "}";
    }

    public String getWarning(){
        return "y{" + output + "}";
    }

    public String getError(){
        return "r{" + output + "}";
    }

    public List<String> getParams(){
        return params;
    }
}
