package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.interfaces.HasFactory;
import javabaseproject.javabase.core.database.models.Model;

@PrimaryKey("id")
public class Book extends Model<Book> implements HasFactory {

    protected int id;
    @Unique
    protected String name;

    public Book(){}

    public Book(int id, String name){
        this.id = id;
        this.name = name;
    }

// ... add more props with protected access modifier
}
