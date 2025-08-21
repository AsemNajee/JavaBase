package javabaseproject.javabase.framework.commandline;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputCommand{

    private Matcher matcher;
    private final HashMap<String, Pattern> commandsPatterns;
    private final String command;
    public InputCommand(String command){
        this.command = command;
        commandsPatterns = new HashMap<>();
        registerAllPatterns();
    }
    public boolean isCommandFor(Class<? extends Command> cmd){
        matcher = commandsPatterns.get(cmd.getName()).matcher(command);
        return matcher.find();
    }

    public Matcher getMatcher(){
        return matcher;
    }

    public boolean equals(String text) {
        return command.equals(text);
    }

    private void registerAllPatterns(){
//        make:model Animal -key=id -keyType=int -f
//        drop:model Animal -force
        commandsPatterns.put(ModelCommands.class.getName(), Pattern.compile("^(?<verb>make|drop):model( )+(?<model>[A-Z][A-Za-z0-9]*)( -key=(?<key>[A-Za-z]+))?( -key[Tt]ype=(?<keyType>(String|int|float|double|byte|long|short)))?(?<factory> -f)?(?<force> -force)?"));
//        db:init, db:migrate, db:migrate Animal, db:seed, db:seed Animal, db:drop
        commandsPatterns.put(DatabaseCommands.class.getName(), Pattern.compile("^db:(?<verb>init|migrate|seed|drop)( )?(?<model>[A-Z][A-Za-z0-9]*)?"));
//        make:factory Animal, drop:factory Animal
        commandsPatterns.put(FactoryCommands.class.getName(), Pattern.compile("^(?<verb>make|drop):factory( )+(?<model>[A-Z][A-Za-z0-9]*)?(Factory)?"));
//        make:seeder Animal, drop:seeder Animal, start:seeder, start:seeder Animal
        commandsPatterns.put(SeederCommands.class.getName(), Pattern.compile("^(?<verb>make|drop|start):seeder( )?(?<model>[A-Z][A-Za-z0-9]*)?(Seeder)?"));
    }
}