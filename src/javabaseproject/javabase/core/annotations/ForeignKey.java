package javabaseproject.javabase.core.annotations;

import javabaseproject.javabase.core.database.models.Model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * add foreign key to another model and link them in the database
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey{
    Class<? extends Model<?>> value();
    String key() default "";

}