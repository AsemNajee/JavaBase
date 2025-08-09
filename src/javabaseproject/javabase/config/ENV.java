package javabaseproject.javabase.config;

 import javabaseproject.javabase.core.database.Drivers;

 public class ENV {
    static 
    {
        DRIVER              = Drivers.MYSQL;
        DATABASE_NAME       = "javabase";
        DEFAULT_PRIMARY_KEY = "id";
        ROOT_PACKAGE        = "javabaseproject";
    };
    
    
    
    public static final Drivers DRIVER;
    public static final String DATABASE_NAME;
    public static final String DEFAULT_PRIMARY_KEY;
    public static final String ROOT_PACKAGE;
}
