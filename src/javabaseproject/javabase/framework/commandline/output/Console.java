package javabaseproject.javabase.framework.commandline.output;

public class Console {
    public static String help(){
        return style("""
               -g{help}                           : show this list
               -g{run}                            : run the project
               -g{make:model} b{<ModelName>}         : create new model and register it
                    y{[-key=<attribute>]}           :> set and attribute as a primary key for model
               -g{drop:model} b{<ModelName>}         : drop the model and delete it from registered models
                    y{[-m]}                         :> delete model java class file from models package
               -g{db:drop}                        : drop all database
               -g{db:migrate}                     : migrate models to the database for first time only
                    b{<ModelName>}                  :> migrate a specific model
               -g{db:init}                        : create the database
               -g{db:fresh}                        : refresh the database [drop, init, migrate]
               -g{make:factory} b{<ModelName>}       : create new factory for the model
               -g{drop:factory} b{<ModelName>}       : drop the factory of the model
               -g{make:seeder} b{<ModelName>}        : create new seeder for the model
               -g{drop:seeder} b{<ModelName>}        : drop the seeder of the model
               -g{start:seeder}                   : start seeding the data from the factory to database
                   b{<ModelName>}                   :> start seeding data only for the model
               -g{0}                              : exit
               """);
    }

    public static String style(String output){
        return output
                .replaceAll("(?s)r\\{(.*?)(?<!\\\\)}", Style.textColor("$1", Colors.RED))
                .replaceAll("(?s)b\\{(.*?)(?<!\\\\)}", Style.textColor("$1", Colors.BLUE))
                .replaceAll("(?s)g\\{(.*?)(?<!\\\\)}", Style.textColor("$1", Colors.GREEN))
                .replaceAll("(?s)w\\{(.*?)(?<!\\\\)}", Style.textColor("$1", Colors.WHITE))
                .replaceAll("(?s)p\\{(.*?)(?<!\\\\)}", Style.textColor("$1", Colors.PURPLE))
                .replaceAll("(?s)k\\{(.*?)(?<!\\\\)}", Style.textColor("$1", Colors.BLACK))
                .replaceAll("(?s)y\\{(.*?)(?<!\\\\)}", Style.textColor("$1", Colors.YELLOW))
                .replaceAll("(?s)r\\[(.*?)(?<!\\\\)]", Style.bgColor("$1", Colors.RED))
                .replaceAll("(?s)b\\[(.*?)(?<!\\\\)]", Style.bgColor("$1", Colors.BLUE))
                .replaceAll("(?s)g\\[(.*?)(?<!\\\\)]", Style.bgColor("$1", Colors.GREEN))
                .replaceAll("(?s)w\\[(.*?)(?<!\\\\)]", Style.bgColor("$1", Colors.WHITE))
                .replaceAll("(?s)p\\[(.*?)(?<!\\\\)]", Style.bgColor("$1", Colors.PURPLE))
                .replaceAll("(?s)k\\[(.*?)(?<!\\\\)]", Style.bgColor("$1", Colors.BLACK))
                .replaceAll("(?s)y\\[(.*?)(?<!\\\\)]", Style.bgColor("$1", Colors.YELLOW))
                .replaceAll("(\\\\)(?<escaped>([}\"\\]]))", "${escaped}"); // delete escapes

    }
}
