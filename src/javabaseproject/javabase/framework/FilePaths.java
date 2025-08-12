package javabaseproject.javabase.framework;

import javabaseproject.javabase.config.ENV;

import java.io.File;

public class FilePaths {
    public static String getModelsPath(){
        return basePath(ENV.MODELS_PACKAGE) + File.separator;
    }
    public static String getFactoriesPath(){
        return basePath(ENV.FACTORIES_PACKAGE) + File.separator;
    }
    public static String getSeedersPath(){
        return basePath(ENV.SEEDERS_PACKAGE) + File.separator;
    }
    public static String getModelPath(String model){
        return getModelsPath() + File.separator + model + ".java";
    }
    public static String getFactoryPath(String model){
        return getFactoriesPath() + File.separator + model + "Factory.java";
    }
    public static String getRegisterFilePath(){
        return frameworkPath("MyModels.java");
    }
    public static String frameworkPath(String path){
        return basePath("javabase") + File.separator + path;
    }
    public static String toPackage(String path){
        return path.replace(File.separator, ".");
    }
    public static String basePath(String path){
        return System.getProperty("user.dir")
                + File.separator + "src"
                + (path.startsWith(ENV.ROOT_PACKAGE) ? "" : File.separator + ENV.ROOT_PACKAGE)
                + File.separator + path.replace(".", File.separator);
    }

    public static String getFactoriesPackage(){
        return ENV.ROOT_PACKAGE + "." + ENV.FACTORIES_PACKAGE;
    }
    public static String getModelsPackage(){
        return ENV.ROOT_PACKAGE + "." + ENV.MODELS_PACKAGE;
    }
    public static String getSeedersPackage(){
        return ENV.ROOT_PACKAGE + "." + ENV.SEEDERS_PACKAGE;
    }

    public static String getModelPackage(String modelName){
        return getModelsPackage() + "." + modelName;
    }


}
