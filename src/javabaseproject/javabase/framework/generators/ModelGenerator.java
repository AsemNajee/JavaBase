package javabaseproject.javabase.framework.generators;

import javabaseproject.ENV;
import javabaseproject.javabase.config.STATIC;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.FilePaths;

public class ModelGenerator {
    private String modelName;
    private String key;
    private String keyType;
    public ModelGenerator(String modelName, String key, String keyType){
        this.modelName = modelName;
        this.key = key;
        this.keyType = keyType;
    }

    public String modelFile(){
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
                    
                    // Don't delete this constructor please (: it will cause a problem
                    public {Model}(){}
                    
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
                    
                // ... add more fields with protected access modifier
                }
                """.replace("{Model}", modelName)
                .replace("{key}", key)
                .replace("{UpperKey}", key.substring(0, 1).toUpperCase() + key.substring(1))
                .replace("{keyType}", keyType)
                .replace("{basePackage}", ENV.ROOT_PACKAGE)
                .replace("{baseModelPackage}", STATIC.BASE_MODEL_CLASS_PACKAGE)
                .replace("{modelsPackage}", FilePaths.getModelsPackage());
    }

    public static String constructorForAllFields(String modelName){
        return constructorForAllFields(Recorder.getRecordedClass(modelName).getClazz());
    }

    public static String constructorForAllFields(Class<? extends Model<?>> clazz){
        var fields = Recorder.getRecordedClass(clazz).getFields().values();
        String constructor = "public " + Recorder.getRecordedClass(clazz).getName() + "(";
        StringBuilder params = new StringBuilder();
        StringBuilder consContent = new StringBuilder();
        for(var item : fields){
            if(!params.isEmpty()){
                params.append(", ");
            }
            params.append(item.getType().getJavaType()).append(" ").append(item.getName());
            consContent.append("\tthis.").append(item.getName()).append(" = ").append(item.getName());
            consContent.append(";\n");
        }
        constructor += params + "){\n";
        constructor += consContent;
        constructor += "}";
        return constructor;
    }
}
