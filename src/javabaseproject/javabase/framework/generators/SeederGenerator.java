package javabaseproject.javabase.framework.generators;

import javabaseproject.ENV;
import javabaseproject.javabase.config.STATIC;
import javabaseproject.javabase.framework.FilePaths;

public class SeederGenerator {
    private String modelName;
    public SeederGenerator(String modelName){
        this.modelName = modelName;
    }

    public String seederFile(){
        return """
                package {seedersPackage};
                                
                import {baseSeederPackage};
                import {basePackage}.javabase.core.database.models.Model;
                import {modelsPackage}.{model};
                                
                public class {model}Seeder extends Seeder {
                    public void run() throws Exception{
                        Model.of({model}.class).factory().create(10);
                    }
                }
                """.replace("{modelsPackage}", FilePaths.getModelsPackage())
                .replace("{basePackage}", ENV.ROOT_PACKAGE)
                .replace("{baseSeederPackage}", STATIC.BASE_SEEDER_CLASS_PACKAGE)
                .replace("{model}", modelName)
                .replace("{seedersPackage}", FilePaths.getSeedersPackage());
    }
}
