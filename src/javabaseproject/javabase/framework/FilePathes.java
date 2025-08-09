package javabaseproject.javabase.framework;

import javabaseproject.javabase.config.ENV;

import java.io.File;

public class FilePathes {
    public static String getModelsPath(){
        return basePath("models") + File.separator;
    }
    public static String getModelPath(String model){
        return getModelsPath() + File.separator + model + ".java";
    }
    public static String getRegisterFilePath(){
        return frameworkPath("MyModels.java");
    }
    public static String frameworkPath(String path){
        return basePath("javabase") + File.separator + path;
    }
    private static String basePath(String path){
        return System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + ENV.ROOT_PACKAGE
                + File.separator + path;
    }
}
