package javabaseproject.javabase.framework.generators;

import javabaseproject.javabase.config.ENV;
import javabaseproject.javabase.config.STATIC;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.FilePaths;

public class FactoryGenerator {

    private String modelName;
    private boolean isNew;
    public FactoryGenerator(String modelName){
        this.modelName = modelName;
        isNew = true;
    }
    public FactoryGenerator(String modelName, boolean isNew){
        this.modelName = modelName;
        this.isNew = isNew;
    }

    public FactoryGenerator(Class<? extends Model<?>> clazz){
        this.modelName = clazz.getName().replaceAll("(.*)\\.([A-Za-z0-9]+)", "$2");
        this.isNew = false;
    }

    public String factoryFile(){
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
                .replace("{model}", modelName)
                .replace("{fakeParameters}", (isNew ? getDefaultFakeParameters() : getFakeParameters()))
                .replace("{factoriesPackage}", FilePaths.getFactoriesPackage());
    }

    private String getFakeParameters(){
        StringBuilder fakers = new StringBuilder();
        for(var field : Recorder.getRecordedClass(modelName).getFields().values()){
            fakers.append("\n\t\t\tFake.").append(getFakerMethodFor(field)).append("(),");
        }
        fakers.delete(fakers.length()-1, fakers.length());
        return fakers.toString();
    }

    private String getDefaultFakeParameters(){
        return """
                Fake.randomNumber(),
                \t\t\t\tFake.name()
                """;
    }

    private String getFakerMethodFor(RecordedClass.RecordedField field){
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
