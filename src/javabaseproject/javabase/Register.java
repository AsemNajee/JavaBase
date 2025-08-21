package javabaseproject.javabase;

import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.RecordedClass;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.FilePaths;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * register all models in {@code ENV.MODELS_PACKAGE}
 */
public class Register {

    private static boolean registerDone;

    private static void registerAll() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String path = FilePaths.toPath(FilePaths.getModelsPackage(), "/");
        var res = Thread.currentThread().getContextClassLoader().getResource(path);
//        if(res == null) throw new RuntimeException("The library not work with you, please tell me on github on telegram");
        if(res == null) return;
        File outputDir = new File(res.getFile());
        if(!outputDir.exists()) throw new RuntimeException("The library not work with you, please tell me on github on telegram");

        if(outputDir.list() == null){
            return;
        }
        for(var item : outputDir.list()){
            Recorder.add((Class<? extends Model<?>>) Class.forName(FilePaths.getModelPackage(item).replace(".class", "")));
        }
        registerDone = true;
    }

    public static HashMap<String, RecordedClass> getRegisteredModels() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!registerDone) {
            registerAll();
        }
        return Recorder.getModels();
    }

    public static HashMap<String, RecordedClass> getModels(){
        if(!registerDone){
            throw new RuntimeException("Register all models, then require them");
        }
        return Recorder.getModels();
    }
}
