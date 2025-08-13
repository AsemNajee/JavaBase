package javabaseproject.javabase.config;

import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.database.models.Model;

/**
 * paths to base classes of the framework
 */
public class STATIC {
    public static final String BASE_MODEL_CLASS_PACKAGE = Model.class.getName();
    public static final String BASE_FACTORY_CLASS_PACKAGE = Factory.class.getName();
    public static final String BASE_SEEDER_CLASS_PACKAGE = Seeder.class.getName();
}
