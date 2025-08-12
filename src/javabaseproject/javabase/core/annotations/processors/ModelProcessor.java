package javabaseproject.javabase.core.annotations.processors;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.database.models.Model;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.util.Set;

public class ModelProcessor extends AbstractProcessor {

//    I got this method from gpt, and I'm just learning
//    this is for compile time errors and need more time to implement
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(Element element : roundEnv.getElementsAnnotatedWith(PrimaryKey.class)){
            if(element.getKind() != ElementKind.CLASS){
                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "@PrimaryKey annotation is compatible only with classes");
                continue;
            }
            boolean classIsModel = false;
            TypeElement telemcent = (TypeElement) element;
            TypeElement superClass = (TypeElement) telemcent.getSuperclass();
            while(superClass.asType().getKind() != TypeKind.NONE) {
                if(superClass.getClass().isNestmateOf(Model.class)){
                    classIsModel = true;
                    break;
                }
                superClass = (TypeElement) superClass.getSuperclass();
            }
            if(!classIsModel){
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "this model most extends Model class");
            }
        }

        return false;
    }
}
