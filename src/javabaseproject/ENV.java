package javabaseproject;

import javabaseproject.javabase.config.Drivers;
import javabaseproject.javabase.config.Languages;

/**
 * configuration file for your application
 * change your application settings and be carefully with this config
 */
public class ENV {
    static 
    {
        DRIVER              = Drivers.MYSQL;
        DATABASE_NAME       = "javabase";
        DEFAULT_PRIMARY_KEY = "id";
        ROOT_PACKAGE        = "javabaseproject";
        LANGUAGE            = Languages.EN;
        MODELS_PACKAGE      = "database.models";
        PIVOTS_PACKAGE      = "database.models";
        FACTORIES_PACKAGE   = "database.factories";
        SEEDERS_PACKAGE     = "database.seeders";
    };


    /**
     * select your driver to connect with database
     * you must add external library of that driver
     */
    public static final Drivers DRIVER;
    /**
     * the database name to stare all data
     * this database will be created when you run command {@code db:init}
     */
    public static final String DATABASE_NAME;
    /**
     * the name of the default of the primary key in any model
     * the default is {@code id} and you can change it from here
     */
    public static final String DEFAULT_PRIMARY_KEY;
    /**
     * the name of your application package
     * this is necessary to run without errors
     * this is necessary to be able to call classes in base package
     * like Main and Handler
     */
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
    /**
     * directory where pivots will create
     * pivots are middle table for many-to-many relations
     */
    public static final String PIVOTS_PACKAGE;
    /**
     * directory where new factories will create in
     * you can read the docs of {@code MODELS_PACKAGE} for more details
     */
    public static final String FACTORIES_PACKAGE;
    /**
     * directory where new seeders will create in
     * you can read the docs of {@code MODELS_PACKAGE} for more details
     */
    public static final String SEEDERS_PACKAGE;
    /**
     * this set your application logs and errors and confirms messages language
     * it's not ready for now and need to implement
     * {@see javabaseproject.javabase.core.database.faker.FakeData}
     */
    public static final Languages LANGUAGE;
}
