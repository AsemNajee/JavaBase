package javabaseproject.javabase.framework.generators;

import javabaseproject.ENV;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.FilePaths;
import javabaseproject.javabase.framework.commandline.controllers.PivotController;

import static javabaseproject.javabase.framework.commandline.controllers.PivotController.toInstanceName;
import static javabaseproject.javabase.framework.commandline.controllers.PivotController.toPluralName;

public class PivotGenerator {
    private String pivotName;
    private String first;
    private String second;
    private RecordedClass.RecordedField firstKey;
    private RecordedClass.RecordedField secondKey;
    private String firstInstance;
    private String secondInstance;

    public PivotGenerator(String pivotName, String first, String second, String firstInstance, String secondInstance) {
        this.pivotName = pivotName;
        this.first = first;
        this.second = second;
        this.firstKey = Recorder.getRecordedClass(first).getPrimaryKey();
        this.secondKey = Recorder.getRecordedClass(second).getPrimaryKey();
        this.firstInstance = firstInstance;
        this.secondInstance = secondInstance;
    }

    public String PivotFile(){
        return """
                package {modelsPackage};
                                
                import {basePackage}.javabase.core.annotations.ForeignKey;
                import {basePackage}.javabase.core.annotations.PrimaryKey;
                import {basePackage}.javabase.core.collections.ModelsCollection;
                import {basePackage}.javabase.core.database.models.Model;
                import {basePackage}.javabase.core.database.models.Relations;
                                
                @PrimaryKey("id")
                public class {pivotName} extends Model<{pivotName}>{
                                
                    protected int id;
                    
                    @ForeignKey({first}.class)
                    protected {firstCast} {firstInstance}{firstKey};
                    
                    @ForeignKey({second}.class)
                    protected {secondCast} {secondInstance}{secondKey};
                    
                    public {pivotName}(){}
                    public {pivotName}(int id, {first} {firstInstance}, {second} {secondInstance}){
                        this.{firstInstance}{firstKey} = ({firstCast}){firstInstance}.getKey();
                        this.{secondInstance}{secondKey} = ({secondCast}){secondInstance}.getKey();
                        this.id = id;
                    }
                                
                    public static ModelsCollection<{second}> belongsToMany({first} {firstInstance}) throws Exception {
                        return Relations.belongsToMany({pivotName}.class, {second}.class, {firstInstance});
                    }
                    public static ModelsCollection<{first}> belongsToMany({second} {secondInstance}) throws Exception {
                        return Relations.belongsToMany({pivotName}.class, {first}.class, {secondInstance});
                    }
                }
                """
                .replace("{basePackage}", ENV.ROOT_PACKAGE)
                .replace("{modelsPackage}", FilePaths.getModelsPackage())
                .replace("{pivotName}", pivotName)
                .replace("{first}", first)
                .replace("{firstKey}", PivotController.toClassName(firstKey.getName()))
                .replace("{firstCast}", firstKey.getType().getJavaType())
                .replace("{firstInstance}", firstInstance)
                .replace("{second}", second)
                .replace("{secondKey}",  PivotController.toClassName(secondKey.getName()))
                .replace("{secondCast}", secondKey.getType().getJavaType())
                .replace("{secondInstance}", secondInstance);
    }

    private String getRelationMethodFor(String returnModel){
        return """
                    public ModelsCollection<{returned}> {name}() throws Exception {
                        return {pivot}.belongsToMany(this);
                    }
                """.replace("{name}", toInstanceName(toPluralName(returnModel)))
                .replace("{pivot}", pivotName)
                .replace("{returned}", returnModel);
    }

    public String getRelationFromFirstModelToSecondModel(){
        return getRelationMethodFor(second);
    }
    public String getRelationFromSecondModelToFirstModel(){
        return getRelationMethodFor(first);
    }
}
