package javabaseproject.javabase.framework.readycontent;

import javabaseproject.javabase.config.ENV;
import javabaseproject.javabase.config.STATIC;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.FilePaths;

public class NewContent {
    public static String modelContent(String modelName, String key, String keyType){
        return """
                package {modelsPackage};
                               
                import {basePackage}.javabase.core.annotations.PrimaryKey;
                import {basePackage}.javabase.core.annotations.Unique;
                import {baseModelPackage};
                               
                @PrimaryKey("{key}")
                public class {Model} extends Model<{Model}>{
                               
                    protected {keyType} {key};
                    @Unique
                    protected String name;
                    
                    public {Model}({keyType} {key}, String name){
                        this.{key} = {key};
                        this.name = name;
                    }
                    
                    public void set{UpperKey}({keyType} {key}){
                        this.{key} = {key};
                    }
                    public {keyType} get{UpperKey}(){
                        return {key};
                    }
                    
                    public void setName(String name){
                        this.name = name;
                    }
                    public String getName(){
                        return name;
                    }
                    
                // ... add more props with protected access modifier
                }
                """.replace("{Model}", modelName)
                .replace("{key}", key)
                .replace("{UpperKey}", key.substring(0, 1).toUpperCase() + key.substring(1))
                .replace("{keyType}", keyType)
                .replace("{basePackage}", ENV.ROOT_PACKAGE)
                .replace("{baseModelPackage}", STATIC.BASE_MODEL_CLASS_PACKAGE)
                .replace("{modelsPackage}", FilePaths.getModelsPackage());
    }

    public static String factoryContent(Class<? extends Model<?>> clazz){
        return factoryContent(
                clazz.getName().replaceAll("(.*)\\.", ""),
                getFakeParameters(clazz));
    }
    public static String factoryContent(String modelName){
        return factoryContent(
                modelName,
                """
                        Fake.randomNumber(),
                        \t\t\t\tFake.name()
                        """);
    }
    private static String factoryContent(String modelName, String fakeParameters){
        return """
                package {factoriesPackage};
                
                import {modelsPackage}.{model};
                import {baseFactoryPackage};
                import {basePackage}.javabase.core.database.faker.Fake;
                
                public class {model}Factory extends Factory<{model}>{
                    public {model} item() {
                        return new {model}(
                                {fakeParameters}
                        );
                    }
                }
                """.replace("{modelsPackage}", FilePaths.getModelsPackage())
                .replace("{basePackage}", ENV.ROOT_PACKAGE)
                .replace("{baseFactoryPackage}", STATIC.BASE_FACTORY_CLASS_PACKAGE)
                .replace("{model}", modelName.substring(0, 1).toUpperCase() + modelName.substring(1))
                .replace("{fakeParameters}", fakeParameters)
                .replace("{factoriesPackage}", FilePaths.getFactoriesPackage());
    }

    private static String getFakeParameters(Class<? extends Model<?>> model){
        StringBuilder fakers = new StringBuilder();
        for(var field : Recorder.getRecordedClass(model).getFields().values()){
            fakers.append("\n\t\t\t\tFake.").append(getFakerMethodFor(field)).append("()");
        }
        return fakers.toString();
    }

    private static String getFakerMethodFor(RecordedClass.RecordedField field){
        return switch (field.getType()){
            case STRING -> switch (field.getName()) {
                case "name", "sentence", "paragraph", "email" -> field.getName();
                case "post" -> "paragraph";
                default -> "sentence";
            };
            case INT, LONG -> "randomNumber";
            case FLOAT, DOUBLE -> "randomFloat";
            case SHORT -> "randomShort";
            case BYTE -> "randomByte";
            case BOOLEAN -> "randomBoolean";
        };
    }
}
