package javabaseproject.javabase.config;

public class ENV {
    static 
    {
        DRIVER              = Drivers.MYSQL;
        DATABASE_NAME       = "javabase";
        DEFAULT_PRIMARY_KEY = "id";
        ROOT_PACKAGE        = "javabaseproject";
        LANGUAGE            = Languages.EN;
        MODELS_PACKAGE      = "database.models";
        FACTORIES_PACKAGE   = "database.factories";
        SEEDERS_PACKAGE     = "database.seeders";
    };
    
    
    
    public static final Drivers DRIVER;
    public static final String DATABASE_NAME;
    public static final String DEFAULT_PRIMARY_KEY;
    public static final String ROOT_PACKAGE;
    /**
     * directory where your models will create
     * you can change it to anywhere you want
     * you can set the value to subdir by using dot like {@code database.models}
     * this will set the default dir for new models as database/models
     * this help to use the best separator dir based on your file system
     * the dir is based on your {@code ROOT_PACKAGE}
     */
    public static final String MODELS_PACKAGE;
    public static final String FACTORIES_PACKAGE;
    public static final String SEEDERS_PACKAGE;
    public static final Languages LANGUAGE;
}
