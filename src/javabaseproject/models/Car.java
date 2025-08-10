package javabaseproject.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;

@PrimaryKey("id")
public class Car extends Model<Car>{

    protected int id;
    @Unique
    protected String name;

// ... add more props with protected access modifier
}
