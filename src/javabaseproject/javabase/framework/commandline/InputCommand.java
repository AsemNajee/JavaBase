package javabaseproject.javabase.framework.commandline;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputCommand{

    private Matcher matcher;
    private final HashMap<String, Pattern> commandsPatterns;
    private String command;
    public InputCommand(String command){
        if(command.equals("0")){
            System.exit(0); // 0 - exit
        }
        this.command = command;
        commandsPatterns = new HashMap<>();
        registerAllPatterns();
    }
    public boolean isCommandFor(Class<? extends Commands> cmd){
        matcher = commandsPatterns.get(cmd.getName()).matcher(command);
        return matcher.find();
    }

    public Matcher getMatcher(){
        return matcher;
    }

    public void registerAllPatterns(){
        commandsPatterns.put(ModelCommands.class.getName(), Pattern.compile("^(?<verb>make|drop):model( )+(?<model>[A-Z][A-Za-z0-9]*)(?<etc> -(key=(?<key>[A-Za-z]+)))*"));
        commandsPatterns.put(DatabaseCommands.class.getName(), Pattern.compile("^db:(?<verb>init|migrate|seed|drop)(?<model> [A-Z][A-Za-z0-9])?"));
    }
}